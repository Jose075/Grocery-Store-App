package com.example.tiendadecomida.ui;

import java.io.Serializable;

public class VerTodoModelo implements Serializable
{
     String nombre;
     String descripcion;
     String rating;
     String img_url;
     String type;
     int precio;


    public VerTodoModelo()
    {
    }

    public VerTodoModelo(String nombre, String descripcion, String rating, String img_url, String type, int precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rating = rating;
        this.img_url = img_url;
        this.type = type;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }
}
