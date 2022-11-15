package com.example.tiendadecomida;

import android.content.BroadcastReceiver;
import android.content.Context;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.activities.PedidoHechoActivity;
import com.example.tiendadecomida.activities.perfilActivity;
import com.example.tiendadecomida.adapters.CarritoAdaptador;
import com.example.tiendadecomida.ui.ModeloCarrito;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.math.PairedStatsAccumulator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CarritoFragment extends Fragment
{

    FirebaseFirestore dba;
    FirebaseAuth autenticar;
    FirebaseFirestore firestore;
    TextView sobreMontoTotal;
    RecyclerView recyclerView;
    CarritoAdaptador carritoAdaptador;
    List<ModeloCarrito> modeloCarritoList;
    ModeloCarrito modeloCarrito;
    String model;
    ProgressBar progressBar;
    Button comprarAhora;
    ConstraintLayout constraintLayout;
    ConstraintLayout constraintLayout2;
    DatabaseReference dbEdit;

    public int montoTotal = 0;


    public CarritoFragment()
    {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_carrito, container, false);

        dba = FirebaseFirestore.getInstance();
        dbEdit = FirebaseDatabase.getInstance().getReference();
        autenticar = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        progressBar = root.findViewById(R.id.progressBarCarrito);
        progressBar.setVisibility(View.VISIBLE);

        comprarAhora = root.findViewById(R.id.comprar_btn);
        recyclerView = root.findViewById(R.id.recyclerCarrito1);
        constraintLayout = root.findViewById(R.id.constrain1);
        constraintLayout2 = root.findViewById(R.id.constrain2);


        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sobreMontoTotal = root.findViewById(R.id.textViewPrecioTotal);
        constraintLayout2.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        comprarAhora.setVisibility(View.GONE);

        LocalBroadcastManager.getInstance(getActivity())
                .registerReceiver(mMensajeReceiver,new IntentFilter("MontoTotal"));

        modeloCarritoList = new ArrayList<>();
        carritoAdaptador = new CarritoAdaptador(getActivity(),modeloCarritoList);
        recyclerView.setAdapter(carritoAdaptador);



        firestore.collection("UsuarioActual").document(autenticar.getCurrentUser().getUid())
                .collection("AñadirCarrito").whereEqualTo("nombreProducto","vencido")
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>()
        {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots)
            {
                WriteBatch batch = FirebaseFirestore.getInstance().batch();


                List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot snapshot : snapshotList)
                {
                    batch.delete(snapshot.getReference());


                }
                batch.commit().addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {

                        //Toast.makeText(getContext(), "Ok", Toast.LENGTH_SHORT).show();

                        dba.collection("UsuarioActual").document(autenticar.getCurrentUser().getUid())
                                .collection("AñadirCarrito").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
                                            carritoAdaptador.notifyDataSetChanged();
                                            modeloCarritoList.add(modeloCarrito);
                                            progressBar.setVisibility(View.GONE);
                                            constraintLayout2.setVisibility(View.VISIBLE);
                                            comprarAhora.setVisibility(View.VISIBLE);
                                            recyclerView.setVisibility(View.VISIBLE);
                                            constraintLayout.setVisibility(View.GONE);
                                        }

                                        calcularMontoTotal(modeloCarritoList);
                                    }

                                    else
                                    {
                                        constraintLayout.setVisibility(View.VISIBLE);
                                        progressBar.setVisibility(View.GONE);

                                    }
                                }
                            }
                        });


                    }
                });
            }
        });




        comprarAhora.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                comprarAhora.setEnabled(false);

                Intent intent = new Intent(getContext(), PedidoHechoActivity.class);


                if(modeloCarritoList.isEmpty())
                {
                    Toast.makeText(getContext(), "No hay productos en el carrito", Toast.LENGTH_SHORT).show();
                    comprarAhora.setEnabled(true);
                }
                else
                {
                    intent.putExtra("listaItem", (Serializable) modeloCarritoList);
                    comprarAhora.setEnabled(true);
                    startActivity(intent);


                }
            }

        });

        return root;
    }

    public void calcularMontoTotal(List<ModeloCarrito> modeloCarritoList)
    {
        CarritoAdaptador caradap = null;
        int sum = 0;
        for(ModeloCarrito modeloCarrito : modeloCarritoList)
        {
            /*sum = Integer.parseInt(modeloCarrito.getCantidadTotal()) * modeloCarrito.getPrecioTotal();
            montoTotal += sum;*/

            montoTotal += modeloCarrito.getPrecioTotal();
        }

        sobreMontoTotal.setText("Factura Total: "+montoTotal+"$");

    }

    public BroadcastReceiver mMensajeReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            int facturaTotal = intent.getIntExtra("montoTotal",0);
            sobreMontoTotal.setText("Factura Total: "+facturaTotal+"$");

        }
    };




}