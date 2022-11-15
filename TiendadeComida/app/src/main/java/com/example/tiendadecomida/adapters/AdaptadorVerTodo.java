package com.example.tiendadecomida.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tiendadecomida.R;
import com.example.tiendadecomida.activities.DetalleProductoActivity;
import com.example.tiendadecomida.activities.VerTodoActivity;
import com.example.tiendadecomida.ui.VerTodoModelo;
import com.example.tiendadecomida.ui.modeloTendencia;

import java.util.List;

public class AdaptadorVerTodo extends RecyclerView.Adapter<AdaptadorVerTodo.ViewHolder>
{
    private Context context;
    private List<VerTodoModelo> verTodoModeloList;

    public AdaptadorVerTodo(Context context, List<VerTodoModelo> verTodoModeloList)
    {
        this.context = context;
        this.verTodoModeloList = verTodoModeloList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ver_todo_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Glide.with(context).load(verTodoModeloList.get(position).getImg_url()).into(holder.imageView);
        holder.nombre.setText(verTodoModeloList.get(position).getNombre());
        holder.rating.setText(verTodoModeloList.get(position).getRating());
        holder.descripcion.setText(verTodoModeloList.get(position).getDescripcion());
        holder.precio.setText(verTodoModeloList.get(position).getPrecio()+"/kg");


        if(verTodoModeloList.get(position).getType().equals("huevo"))
        {
            holder.precio.setText(verTodoModeloList.get(position).getPrecio()+"/docena");
        }

        if(verTodoModeloList.get(position).getType().equals("leche"))
        {
            holder.precio.setText(verTodoModeloList.get(position).getPrecio()+"/litro");
        }

        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, DetalleProductoActivity.class);
                intent.putExtra("detalle",verTodoModeloList.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount()
    {
        return verTodoModeloList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        TextView nombre,descripcion,rating,precio;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.verTodo_img);
            nombre = itemView.findViewById(R.id.verTodo_nombre);
            descripcion = itemView.findViewById(R.id.descripcion_verTodo);
            rating = itemView.findViewById(R.id.vertodo_rating);
            precio = itemView.findViewById(R.id.verTodo_precio);


        }
    }
}
