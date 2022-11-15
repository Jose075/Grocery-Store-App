package com.example.tiendadecomida.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.activities.VerTodoActivity;
import com.example.tiendadecomida.ui.modeloTendencia;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorTendencia extends RecyclerView.Adapter<AdaptadorTendencia.ViewHolder>
{

    private Context context;
    private List<modeloTendencia> tedenciaModeloLista;

    public AdaptadorTendencia(Context context, List<modeloTendencia> tedenciaModeloLista) {
        this.context = context;
        this.tedenciaModeloLista = tedenciaModeloLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.tendencia_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Glide.with(context).load(tedenciaModeloLista.get(position).getImg_url()).into(holder.popImg);
        holder.nombre.setText(tedenciaModeloLista.get(position).getNombre());
        holder.rating.setText(tedenciaModeloLista.get(position).getRating());
        holder.descripcion.setText(tedenciaModeloLista.get(position).getDescripcion());
        holder.descuento.setText(tedenciaModeloLista.get(position).getDescuento());

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, VerTodoActivity.class);
                intent.putExtra("type",tedenciaModeloLista.get(position).getType());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return tedenciaModeloLista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView popImg;
        TextView nombre,descripcion,rating,descuento;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            popImg = itemView.findViewById(R.id.pop_img);
            nombre = itemView.findViewById(R.id.pop_nombre);
            descripcion = itemView.findViewById(R.id.pop_descripcion);
            rating = itemView.findViewById(R.id.pop_rating);
            descuento = itemView.findViewById(R.id.pop_descuento);

        }
    }
}
