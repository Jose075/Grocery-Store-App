package com.example.tiendadecomida.ui;

public class modeloTendencia
{
    String nombre;
    String descripcion;
    String rating;
    String descuento;
    String type;
    String img_url;

    public modeloTendencia()
    {

    }

    public modeloTendencia(String nombre, String descripcion, String rating, String descuento, String type, String img_url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rating = rating;
        this.descuento = descuento;
        this.type = type;
        this.img_url = img_url;
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

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
