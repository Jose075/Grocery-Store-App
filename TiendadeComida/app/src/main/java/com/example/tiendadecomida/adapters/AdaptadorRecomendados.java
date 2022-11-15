package com.example.tiendadecomida.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.ImageViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.activities.DetalleProductoActivity;
import com.example.tiendadecomida.ui.ModeloRecomendados;
import com.example.tiendadecomida.ui.modeloTendencia;

import java.util.List;

public class AdaptadorRecomendados extends RecyclerView.Adapter<AdaptadorRecomendados.ViewHolder>
{
    private Context context;
    private List<ModeloRecomendados> modeloRecomendados;

    public AdaptadorRecomendados(Context context, List<ModeloRecomendados> modeloRecomendados)
    {
        this.context = context;
        this.modeloRecomendados = modeloRecomendados;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recomendados_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Glide.with(context).load(modeloRecomendados.get(position).getImg_url()).into(holder.imageView);
        holder.nombre.setText(modeloRecomendados.get(position).getNombre());
        holder.rating.setText(modeloRecomendados.get(position).getRating());
        holder.descripcion.setText(modeloRecomendados.get(position).getDescripcion());


       /*if(modeloRecomendados.get(position).getNombre().equals("huevo"))
        {
            holder.precio.setText(verTodoModeloList.get(position).getPrecio()+"/docena");
        }

        if(verTodoModeloList.get(position).getType().equals("leche"))
        {
            holder.precio.setText(verTodoModeloList.get(position).getPrecio()+"/litro");
        }*/

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, DetalleProductoActivity.class);
                intent.putExtra("detalle",modeloRecomendados.get(position));
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount()
    {
        return modeloRecomendados.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView nombre,descripcion,rating;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.recomendado_img);
            nombre = itemView.findViewById(R.id.recomendado_nombre);
            descripcion = itemView.findViewById(R.id.recomendado_descuento);
            rating = itemView.findViewById(R.id.recomendado_rating);


        }
    }
}
