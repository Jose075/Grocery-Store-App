package com.example.tiendadecomida.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.ui.ModeloRecomendados;
import com.example.tiendadecomida.ui.VerTodoModelo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class DetalleProductoActivity extends AppCompatActivity
{

    TextView cantidad;
    int cantidadTotal = 1;
    int precioTotal = 0;

    ImageView detalleImg;
    TextView precio,rating,descripcion;
    Button añadirCarrito;
    ImageView añadirItem,restarItem;
    Toolbar toolbar;

    FirebaseFirestore firestore;
    FirebaseAuth autenticar;

    VerTodoModelo verTodoModelo = null;
    ModeloRecomendados modeloRecomendados = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);

        toolbar = findViewById(R.id.barraTitulo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firestore = FirebaseFirestore.getInstance();
        autenticar = FirebaseAuth.getInstance();

        final Object object = getIntent().getSerializableExtra("detalle");
        if(object instanceof VerTodoModelo)
        {
            verTodoModelo = (VerTodoModelo) object;

        }

        if(object instanceof  ModeloRecomendados)
        {
            modeloRecomendados = (ModeloRecomendados) object;
        }

        cantidad = findViewById(R.id.cantidad);
        detalleImg = findViewById(R.id.detalle_img);
        precio = findViewById(R.id.precioTextDetalle);
        rating = findViewById(R.id.detalle_rating);
        descripcion = findViewById(R.id.descripcion_detalle);
        añadirItem = findViewById(R.id.añadir_item);
        restarItem = findViewById(R.id.restar_item);


        if(verTodoModelo != null)
        {
            Glide.with(getApplicationContext()).load(verTodoModelo.getImg_url()).into(detalleImg);
            rating.setText(verTodoModelo.getRating());
            descripcion.setText(verTodoModelo.getDescripcion());
            precio.setText("Precio: $"+ verTodoModelo.getPrecio()+"/kg");

            precioTotal = verTodoModelo.getPrecio() * cantidadTotal;

            if(verTodoModelo.getType().equals("huevo"))
            {
                precio.setText("Precio: $"+ verTodoModelo.getPrecio()+"/docena");
                precioTotal = verTodoModelo.getPrecio() * cantidadTotal;
            }

            if(verTodoModelo.getType().equals("leche"))
            {
                precio.setText("Precio: $"+ verTodoModelo.getPrecio()+"/litro");
                precioTotal = verTodoModelo.getPrecio() * cantidadTotal;
            }


        }

        añadirCarrito = findViewById(R.id.añadir_carrito);
        añadirCarrito.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                carritoAñadido();

            }
        });
        añadirItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cantidadTotal < 10)
                {
                    cantidadTotal++;
                    cantidad.setText(String.valueOf(cantidadTotal));
                    precioTotal = verTodoModelo.getPrecio() * cantidadTotal;
                }
            }
        });

        restarItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(cantidadTotal > 1)
                {
                    cantidadTotal--;
                    cantidad.setText(String.valueOf(cantidadTotal));
                    precioTotal = verTodoModelo.getPrecio() * cantidadTotal;
                }
            }
        });


    }

    private void carritoAñadido()
    {
        añadirCarrito.setEnabled(false);
        String guardarFechaActual, guardarTiempoActual;
        Calendar calendarFecha = Calendar.getInstance();

        SimpleDateFormat fechaActual = new SimpleDateFormat("dd/MM/yyyy");
        guardarFechaActual = fechaActual.format(calendarFecha.getTime());

        SimpleDateFormat tiempoActual = new SimpleDateFormat("HH: mm:ss a");
        guardarTiempoActual = tiempoActual.format(calendarFecha.getTime());


        final HashMap<String,Object> carritoMap = new HashMap<>();

        carritoMap.put("nombreProducto",verTodoModelo.getNombre());
        carritoMap.put("precioProducto",precio.getText().toString());
        carritoMap.put("fechaActual",guardarFechaActual);
        carritoMap.put("tiempoActual",guardarTiempoActual);
        carritoMap.put("cantidadTotal",cantidad.getText().toString());
        carritoMap.put("precioTotal",precioTotal);

        firestore.collection("UsuarioActual").document(autenticar.getCurrentUser().getUid())
                .collection("AñadirCarrito").add(carritoMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task)
            {
                Toast.makeText(DetalleProductoActivity.this, "Añadido al Carrito", Toast.LENGTH_SHORT).show();
                finish();
                añadirCarrito.setEnabled(true);
            }
        });

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