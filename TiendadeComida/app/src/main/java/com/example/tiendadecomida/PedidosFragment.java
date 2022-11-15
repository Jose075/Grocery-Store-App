package com.example.tiendadecomida;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendadecomida.activities.PedidoHechoActivity;
import com.example.tiendadecomida.adapters.AdaptadorPedidos;
import com.example.tiendadecomida.adapters.CarritoAdaptador;
import com.example.tiendadecomida.ui.ModeloCarrito;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PedidosFragment extends Fragment
{
    FirebaseFirestore dba;
    FirebaseAuth autenticar;
    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    AdaptadorPedidos adaptadorPedidos;
    List<ModeloCarrito> modeloCarritoList;
    ModeloCarrito modeloCarrito;
    String model;
    ProgressBar progressBar;
    Toolbar toolbar;
    ConstraintLayout constraintLayout;
    ConstraintLayout constraintLayout2;
    DatabaseReference dbEdit;

    public PedidosFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_pedidos, container, false);



        dba = FirebaseFirestore.getInstance();
        dbEdit = FirebaseDatabase.getInstance().getReference();
        autenticar = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressBar = root.findViewById(R.id.progressBarPedidos);
        progressBar.setVisibility(View.VISIBLE);


        recyclerView = root.findViewById(R.id.recyclerPedidos);
        constraintLayout = root.findViewById(R.id.constrainPedido1);
        constraintLayout2 = root.findViewById(R.id.constrainPedido2);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        constraintLayout2.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);


        modeloCarritoList = new ArrayList<>();
        adaptadorPedidos = new AdaptadorPedidos(getActivity(),modeloCarritoList);
        recyclerView.setAdapter(adaptadorPedidos);



        dba.collection("UsuarioActual").document(autenticar.getCurrentUser().getUid())
                .collection("Pedido").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if(task.isSuccessful())
                {
                    if(task.getResult().size() > 0)
                    {
                        for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments())
                        {


                            String documentoId = documentSnapshot.getId();

                            ModeloCarrito modeloCarrito = documentSnapshot.toObject(ModeloCarrito.class);

                            modeloCarrito.setDocumentoId(documentoId);
                            adaptadorPedidos.notifyDataSetChanged();
                            modeloCarritoList.add(modeloCarrito);
                            progressBar.setVisibility(View.GONE);
                            constraintLayout2.setVisibility(View.VISIBLE);
                            recyclerView.setVisibility(View.VISIBLE);
                            constraintLayout.setVisibility(View.GONE);
                            //Toast.makeText(getContext(), "cargado", Toast.LENGTH_SHORT).show();

                        }

                    }

                    else
                    {
                        constraintLayout.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);

                    }
                }
            }
        });


        return root;























        // Inflate the layout for this fragment
        /*return inflater.inflate(R.layout.fragment_pedidos, container, false);*/
    }
}