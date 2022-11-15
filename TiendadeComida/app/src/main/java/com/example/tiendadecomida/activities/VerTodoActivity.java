package com.example.tiendadecomida.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.widget.Toolbar;

import com.example.tiendadecomida.MainActivity;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.adapters.AdaptadorVerTodo;
import com.example.tiendadecomida.ui.VerTodoModelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class VerTodoActivity extends AppCompatActivity
{


    FirebaseFirestore firestore;
    RecyclerView recyclerView;
    AdaptadorVerTodo adaptadorVerTodo;
    List<VerTodoModelo> verTodoModeloList;
    Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_todo);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = findViewById(R.id.progressBarVer);
        progressBar.setVisibility(View.VISIBLE);

        firestore = FirebaseFirestore.getInstance();
        String type = getIntent().getStringExtra("type");
        recyclerView = findViewById(R.id.verTodoRecycler);
        recyclerView.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        verTodoModeloList = new ArrayList<>();
        adaptadorVerTodo = new AdaptadorVerTodo(this,verTodoModeloList);
        recyclerView.setAdapter(adaptadorVerTodo);


        //obtener frutas
        if(type != null && type.equalsIgnoreCase("fruta")) {
            firestore.collection("TodosProductos").whereEqualTo("type", "fruta").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        VerTodoModelo verTodoModelo = documentSnapshot.toObject(VerTodoModelo.class);
                        verTodoModeloList.add(verTodoModelo);
                        adaptadorVerTodo.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }



            //obtener vegetales
            if(type != null && type.equalsIgnoreCase("vegetal"))
            {
                firestore.collection("TodosProductos").whereEqualTo("type","vegetal").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments())
                        {
                            VerTodoModelo verTodoModelo = documentSnapshot.toObject(VerTodoModelo.class);
                            verTodoModeloList.add(verTodoModelo);
                            adaptadorVerTodo.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    }
                });

        }



        //obtener pescado
        if(type != null && type.equalsIgnoreCase("pescado"))
        {
            firestore.collection("TodosProductos").whereEqualTo("type","pescado").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments())
                    {
                        VerTodoModelo verTodoModelo = documentSnapshot.toObject(VerTodoModelo.class);
                        verTodoModeloList.add(verTodoModelo);
                        adaptadorVerTodo.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }


        //obtener Leche
        if(type != null && type.equalsIgnoreCase("leche"))
        {
            firestore.collection("TodosProductos").whereEqualTo("type","leche").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments())
                    {
                        VerTodoModelo verTodoModelo = documentSnapshot.toObject(VerTodoModelo.class);
                        verTodoModeloList.add(verTodoModelo);
                        adaptadorVerTodo.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }


        //obtener huevo
        if(type != null && type.equalsIgnoreCase("huevo"))
        {
            firestore.collection("TodosProductos").whereEqualTo("type","huevo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task)
                {
                    for(DocumentSnapshot documentSnapshot: task.getResult().getDocuments())
                    {
                        VerTodoModelo verTodoModelo = documentSnapshot.toObject(VerTodoModelo.class);
                        verTodoModeloList.add(verTodoModelo);
                        adaptadorVerTodo.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });

        }


        //obtener productos
        if(type != null && type.equalsIgnoreCase("producto")) {
            firestore.collection("TodosProductos").whereEqualTo("type", "producto").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                        VerTodoModelo verTodoModelo = documentSnapshot.toObject(VerTodoModelo.class);
                        verTodoModeloList.add(verTodoModelo);
                        adaptadorVerTodo.notifyDataSetChanged();
                        progressBar.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here

                /*Intent intent = new Intent(VerTodoActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;*/

                onBackPressed();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}