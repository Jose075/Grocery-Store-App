package com.example.tiendadecomida.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.ui.ModeloNavCategoria;
import com.example.tiendadecomida.ui.ModeloRecomendados;

import java.util.List;

public class AdaptadorNavCategoria extends RecyclerView.Adapter<AdaptadorNavCategoria.ViewHolder>
{
    Context context;
    List<ModeloNavCategoria> modeloNavCategoriaList;

    public AdaptadorNavCategoria(Context context, List<ModeloNavCategoria> modeloNavCategoriaList)
    {
        this.context = context;
        this.modeloNavCategoriaList = modeloNavCategoriaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.nav_categoria_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Glide.with(context).load(modeloNavCategoriaList.get(position).getImg_url()).into(holder.imageView);
        holder.nombre.setText(modeloNavCategoriaList.get(position).getNombre());
        holder.descripcion.setText(modeloNavCategoriaList.get(position).getDescripcion());
        holder.descuento.setText(modeloNavCategoriaList.get(position).getDescuento());
    }

    @Override
    public int getItemCount() {
        return modeloNavCategoriaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView nombre,descripcion,descuento;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.categoria_nav_img);
            nombre = itemView.findViewById(R.id.nav_categoria_nombre);
            descripcion = itemView.findViewById(R.id.nav_categoria_Descripcion);
            descuento = itemView.findViewById(R.id.nav_categoria_Descuento);
        }
    }
}
