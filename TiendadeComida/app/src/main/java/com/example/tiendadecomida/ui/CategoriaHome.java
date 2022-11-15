package com.example.tiendadecomida.ui;

public class CategoriaHome
{
    String nombre;
    String img_url;
    String type;


    public CategoriaHome()
    {

    }


    public CategoriaHome(String nombre, String img_url, String type)
    {
        this.nombre = nombre;
        this.img_url = img_url;
        this.type = type;
    }


    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getImg_url()
    {
        return img_url;
    }

    public void setImg_url(String img_url)
    {
        this.img_url = img_url;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
