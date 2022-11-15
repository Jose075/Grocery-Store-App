package com.example.tiendadecomida.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendadecomida.R;
import com.example.tiendadecomida.adapters.AdaptadorHome;
import com.example.tiendadecomida.adapters.AdaptadorRecomendados;
import com.example.tiendadecomida.adapters.AdaptadorTendencia;
import com.example.tiendadecomida.adapters.AdaptadorVerTodo;
import com.example.tiendadecomida.databinding.FragmentHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {


    //productos_tendencia
    ScrollView scrollView;
    ProgressBar progressBar;
    Button boton;

    RecyclerView tendenciaReclycler,homeRecycler, recomendadoRecycler;


    FirebaseFirestore dba;
    List<modeloTendencia> modeloTendenciaList;
    AdaptadorTendencia adaptadorTendencia;


    ////////////////////Buscar vista

    EditText buscar;
    private List<VerTodoModelo> verTodoModeloList;
    private RecyclerView recyclerViewBuscar;
    private AdaptadorVerTodo adaptadorVerTodo;
    FirebaseAuth autenticar;

    //Categoria Home

    List<CategoriaHome> categoriaHomeList;
    AdaptadorHome adaptadorHome;


    //Recomendado Home
    List<ModeloRecomendados> modeloRecomendadosList;
    AdaptadorRecomendados adaptadorRecomendados ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        dba = FirebaseFirestore.getInstance();


        autenticar = FirebaseAuth.getInstance();
        tendenciaReclycler = root.findViewById(R.id.recyclerPopular);
        homeRecycler = root.findViewById(R.id.recyclerVer);
        recomendadoRecycler = root.findViewById(R.id.recyclerRecomendado);
        scrollView = root.findViewById(R.id.scroll_view);
        progressBar = root.findViewById(R.id.progressbarHome);

        progressBar.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);

        tendenciaReclycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        modeloTendenciaList = new ArrayList<>();
        adaptadorTendencia = new AdaptadorTendencia(getActivity(),modeloTendenciaList);
        tendenciaReclycler.setAdapter(adaptadorTendencia);

        dba.collection("ProductosTendencia")
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
                                modeloTendencia modeloTendencia = document.toObject(modeloTendencia.class);
                                modeloTendenciaList.add(modeloTendencia);
                                adaptadorTendencia.notifyDataSetChanged();

                                progressBar.setVisibility(View.GONE);
                                scrollView.setVisibility(View.VISIBLE);

                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error"+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });






        //Categoria Home
        homeRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoriaHomeList = new ArrayList<>();
        adaptadorHome = new AdaptadorHome(getActivity(),categoriaHomeList);
        homeRecycler.setAdapter(adaptadorHome);

        dba.collection("CategoriaHome")
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
                                CategoriaHome categoriaHome = document.toObject(CategoriaHome.class);
                                categoriaHomeList.add(categoriaHome);
                                adaptadorHome.notifyDataSetChanged();

                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error"+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Recomendados
        /*recomendadoRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        modeloRecomendadosList = new ArrayList<>();
        adaptadorRecomendados = new AdaptadorRecomendados(getActivity(),modeloRecomendadosList);
        recomendadoRecycler.setAdapter(adaptadorRecomendados);

        dba.collection("Recomendado")
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
                                ModeloRecomendados modeloRecomendados = document.toObject(ModeloRecomendados.class);
                                modeloRecomendadosList.add(modeloRecomendados);
                                adaptadorRecomendados.notifyDataSetChanged();

                            }
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Error"+task.getException() , Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

        ////////////BUSCAR
        recyclerViewBuscar = root.findViewById(R.id.buscarRecycler);
        buscar = root.findViewById(R.id.buscarEdit);
        verTodoModeloList = new ArrayList<>();
        adaptadorVerTodo = new AdaptadorVerTodo(getContext(),verTodoModeloList);
        recyclerViewBuscar.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewBuscar.setAdapter(adaptadorVerTodo);
        recyclerViewBuscar.setHasFixedSize(true);
        buscar.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                if(s.toString().isEmpty())
                {
                    verTodoModeloList.clear();
                    adaptadorVerTodo.notifyDataSetChanged();
                }

                else
                {
                    buscarProducto(s.toString());
                }
            }
        });


        return root;
    }

    private void buscarProducto(String type)
    {
        String firstLetterCapital = type.substring(0, 1).toUpperCase() + type.substring(1);
        if(!type.isEmpty())
        {
            dba.collection("TodosProductos").whereEqualTo("nombre",firstLetterCapital).get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task)
                        {
                            if(task.isSuccessful() && task.getResult() != null)
                            {
                                verTodoModeloList.clear();
                                adaptadorVerTodo.notifyDataSetChanged();
                                for(DocumentSnapshot doc : task.getResult().getDocuments())
                                {
                                    VerTodoModelo verTodoModelo = doc.toObject(VerTodoModelo.class);
                                    verTodoModeloList.add(verTodoModelo);
                                    adaptadorVerTodo.notifyDataSetChanged();
                                }
                            }
                        }
                    });
        }
    }

}