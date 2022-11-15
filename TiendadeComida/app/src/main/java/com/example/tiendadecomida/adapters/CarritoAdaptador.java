package com.example.tiendadecomida.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.tiendadecomida.CarritoFragment;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.ui.ModeloCarrito;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public class CarritoAdaptador extends RecyclerView.Adapter<CarritoAdaptador.ViewHolder> {

    Context context;
    List<ModeloCarrito> modeloCarritoList;
    public int precioTotal = 0;
    FirebaseFirestore firestore;
    FirebaseAuth autenticar;
    String pase;
    int resta = 0;
    int contador = 0;


    public CarritoAdaptador(Context context, List<ModeloCarrito> modeloCarritoList) {
        this.context = context;
        this.modeloCarritoList = modeloCarritoList;
        firestore = FirebaseFirestore.getInstance();
        autenticar = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public CarritoAdaptador.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.carrito_item, parent, false));



    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdaptador.ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        holder.nombre.setText(modeloCarritoList.get(position).getNombreProducto());
        holder.precio.setText(modeloCarritoList.get(position).getPrecioProducto());
        holder.fecha.setText(modeloCarritoList.get(position).getFechaActual());
        holder.tiempo.setText(modeloCarritoList.get(position).getTiempoActual());
        holder.cantidad.setText(modeloCarritoList.get(position).getCantidadTotal());
        holder.preciototal.setText(String.valueOf(modeloCarritoList.get(position).getPrecioTotal()));




        holder.borrarItem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                holder.borrarItem.setEnabled(false);
                firestore.collection("UsuarioActual").document(autenticar.getCurrentUser().getUid())
                        .collection("AñadirCarrito")
                        .document(modeloCarritoList.get(position).getDocumentoId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {

                                    for(ModeloCarrito modeloCarrito : modeloCarritoList)
                                    {
                                        precioTotal += modeloCarrito.getPrecioTotal();
                                    }


                                    //precioTotal = precioTotal - modeloCarritoList.get(position).getPrecioTotal();
                                    resta = precioTotal - modeloCarritoList.get(position).getPrecioTotal();
                                    precioTotal = resta;
                                    Intent intent = new Intent("MontoTotal");
                                    intent.putExtra("montoTotal", precioTotal);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                    resta = 0;
                                    modeloCarritoList.remove(modeloCarritoList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Producto Borrado", Toast.LENGTH_SHORT).show();
                                    holder.borrarItem.setEnabled(true);
                                    contador++;
                                    precioTotal = 0;



                                }
                            }
                        });
            }
        });


/*        if(contador == 0)
        {
            calcularMontoTotal(modeloCarritoList);
        }
*/

       /* if(contador == 0)
        {
            precioTotal = precioTotal + modeloCarritoList.get(position).getPrecioTotal()/* - resta;
            Intent intent = new Intent("MontoTotal");
            intent.putExtra("montoTotal", precioTotal);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
        }*/








    }

    @Override
    public int getItemCount() {
        return modeloCarritoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombre, precio, fecha, tiempo, cantidad, preciototal;
        ImageView borrarItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombreProductoCompras);
            precio = itemView.findViewById(R.id.precioCompras);
            fecha = itemView.findViewById(R.id.fechaActualCompras);
            tiempo = itemView.findViewById(R.id.horaCompras);
            cantidad = itemView.findViewById(R.id.cantidadTotalCompras);
            preciototal = itemView.findViewById(R.id.precioTotalCompras);
            borrarItem = itemView.findViewById(R.id.borrar);



        }
    }


    /*public void calcularMontoTotal(List<ModeloCarrito> modeloCarritoList)
    {
        int cantidad = 0;

        for(ModeloCarrito modeloCarrito : modeloCarritoList)
        {
            cantidad += modeloCarrito.getPrecioTotal();
        }


    }*/


}
