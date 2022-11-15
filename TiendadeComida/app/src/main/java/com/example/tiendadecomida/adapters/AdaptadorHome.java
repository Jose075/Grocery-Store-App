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
import com.example.tiendadecomida.ui.CategoriaHome;

import java.util.List;

public class AdaptadorHome extends RecyclerView.Adapter<AdaptadorHome.ViewHolder>
{

    private Context context;
    private List<CategoriaHome> categoriaHomeList;

    public AdaptadorHome(Context context, List<CategoriaHome> categoriaHomeList)
    {
        this.context = context;
        this.categoriaHomeList = categoriaHomeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_categoria_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position)
    {
        Glide.with(context).load(categoriaHomeList.get(position).getImg_url()).into(holder.categoriaImg);
        holder.nombre.setText(categoriaHomeList.get(position).getNombre());


        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, VerTodoActivity.class);
                intent.putExtra("type",categoriaHomeList.get(position).getType());

                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount()
    {
        return categoriaHomeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView categoriaImg;
        TextView nombre;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            categoriaImg = itemView.findViewById(R.id.categoria_home_img);
            nombre = itemView.findViewById(R.id.categoria_home_nombre);
        }

    }
}
