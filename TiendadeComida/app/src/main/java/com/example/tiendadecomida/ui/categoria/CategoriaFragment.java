package com.example.tiendadecomida.ui.categoria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendadecomida.R;
import com.example.tiendadecomida.adapters.AdaptadorHome;
import com.example.tiendadecomida.adapters.AdaptadorNavCategoria;
import com.example.tiendadecomida.ui.CategoriaHome;
import com.example.tiendadecomida.ui.ModeloNavCategoria;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class CategoriaFragment extends Fragment
{


    RecyclerView recyclerView;
    List<ModeloNavCategoria> modeloNavCategoriaList;
    AdaptadorNavCategoria adaptadorNavCategoria;
    FirebaseFirestore dba;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        View root = inflater.inflate(R.layout.fragment_categoria,container,false);


        dba = FirebaseFirestore.getInstance();
        recyclerView = root.findViewById(R.id.categoria_recycler);


        //Categoria Home
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        modeloNavCategoriaList = new ArrayList<>();
        adaptadorNavCategoria = new AdaptadorNavCategoria(getActivity(),modeloNavCategoriaList);
        recyclerView.setAdapter(adaptadorNavCategoria);

        dba.collection("NavCategoria")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                ModeloNavCategoria modeloNavCategoria = document.toObject(ModeloNavCategoria.class);
                                modeloNavCategoriaList.add(modeloNavCategoria);
                                adaptadorNavCategoria.notifyDataSetChanged();

                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error"+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });




        return root;
    }


}