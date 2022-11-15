package com.example.tiendadecomida.activities;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.tiendadecomida.CarritoFragment;
import com.example.tiendadecomida.MainActivity;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.adapters.CarritoAdaptador;
import com.example.tiendadecomida.ui.ModeloCarrito;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PedidoHechoActivity extends AppCompatActivity
{

    FirebaseAuth autenticar;
    FirebaseFirestore firestore;
    Toolbar toolbar;

    @Override
    public void onBackPressed() {


        this.startActivity(new Intent(PedidoHechoActivity.this,MainActivity.class));
        return;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_hecho);

        toolbar = findViewById(R.id.toolbarProducto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autenticar = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        toolbar = findViewById(R.id.toolbarProducto);


        List<ModeloCarrito> modeloCarritoList = (ArrayList<ModeloCarrito>) getIntent().getSerializableExtra("listaItem");

        if(modeloCarritoList != null && modeloCarritoList.size() > 0)
        {
            for(ModeloCarrito modeloCarrito : modeloCarritoList)
            {
                final HashMap<String,Object> carritoMap = new HashMap<>();

                carritoMap.put("nombreProducto",modeloCarrito.getNombreProducto());
                carritoMap.put("precioProducto",modeloCarrito.getPrecioProducto());
                carritoMap.put("fechaActual",modeloCarrito.getFechaActual());
                carritoMap.put("tiempoActual",modeloCarrito.getTiempoActual());
                carritoMap.put("cantidadTotal",modeloCarrito.getCantidadTotal());
                carritoMap.put("precioTotal",modeloCarrito.getPrecioTotal());

                firestore.collection("UsuarioActual").document(autenticar.getCurrentUser().getUid())
                        .collection("Pedido").add(carritoMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task)
                    {
                        Toast.makeText(PedidoHechoActivity.this, "Orden Realizada Exitosamente", Toast.LENGTH_SHORT).show();




                        firestore.collection("UsuarioActual").document(autenticar.getCurrentUser().getUid())
                                .collection("AñadirCarrito").document(modeloCarrito.getDocumentoId())
                                .update("nombreProducto","vencido").addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if(task.isSuccessful())
                                {

                                }
                                else
                                {
                                }
                            }
                        });






                    }
                });
            }
        }



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here


                onBackPressed();

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}