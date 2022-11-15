package com.example.tiendadecomida.ui;

import java.io.Serializable;

public class ModeloCarrito implements Serializable
{
    String nombreProducto;
    String precioProducto;
    String fechaActual;
    String tiempoActual;
    String cantidadTotal;
    int precioTotal;
    String documentoId;
    String tipo;

    public ModeloCarrito()
    {
    }


    public ModeloCarrito(String nombreProducto, String precioProducto, String fechaActual, String tiempoActual, String cantidadTotal, int precioTotal)
    {
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.fechaActual = fechaActual;
        this.tiempoActual = tiempoActual;
        this.cantidadTotal = cantidadTotal;
        this.precioTotal = precioTotal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDocumentoId() {
        return documentoId;
    }

    public void setDocumentoId(String documentoId) {
        this.documentoId = documentoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(String precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(String fechaActual) {
        this.fechaActual = fechaActual;
    }

    public String getTiempoActual() {
        return tiempoActual;
    }

    public void setTiempoActual(String tiempoActual) {
        this.tiempoActual = tiempoActual;
    }

    public String getCantidadTotal() {
        return cantidadTotal;
    }

    public void setCantidadTotal(String cantidadTotal) {
        this.cantidadTotal = cantidadTotal;
    }

    public int getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(int precioTotal) {
        this.precioTotal = precioTotal;
    }
}
