package com.example.tiendadecomida.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiendadecomida.R;
import com.example.tiendadecomida.ui.ModeloCarrito;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdaptadorPedidos extends RecyclerView.Adapter<AdaptadorPedidos.ViewHolder>
{

    Context context;
    List<ModeloCarrito> modeloCarritoList;
    FirebaseFirestore firestore;
    FirebaseAuth autenticar;
    String pase;

    public AdaptadorPedidos(Context context, List<ModeloCarrito> modeloCarritoList)
    {
        this.context = context;
        this.modeloCarritoList = modeloCarritoList;
        firestore = FirebaseFirestore.getInstance();
        autenticar = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public AdaptadorPedidos.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new AdaptadorPedidos.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.pedidos_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorPedidos.ViewHolder holder, @SuppressLint("RecyclerView") int position)
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
                        .collection("Pedido")
                        .document(modeloCarritoList.get(position).getDocumentoId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<Void> task)
                            {
                                if (task.isSuccessful())
                                {
                                    modeloCarritoList.remove(modeloCarritoList.get(position));
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Producto Borrado", Toast.LENGTH_SHORT).show();
                                    holder.borrarItem.setEnabled(true);



                                }
                            }
                        });
            }
        });










    }

    @Override
    public int getItemCount()
    {
        return modeloCarritoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView nombre, precio, fecha, tiempo, cantidad, preciototal;
        ImageView borrarItem;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            nombre = itemView.findViewById(R.id.nombreProductoPedido);
            precio = itemView.findViewById(R.id.precioPedidos);
            fecha = itemView.findViewById(R.id.fechaActualPedidos);
            tiempo = itemView.findViewById(R.id.horaPedido);
            cantidad = itemView.findViewById(R.id.cantidadTotalPedido);
            preciototal = itemView.findViewById(R.id.precioTotalPedido);
            borrarItem = itemView.findViewById(R.id.borrarPedido);
        }
    }
}
