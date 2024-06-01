package app.ij.mlwithtensorflowlite.models;

import com.google.gson.Gson;

public class Bank {
    private String nombre;
    private String compra;
    private String venta;

    // Getters and Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCompra() {
        return compra;
    }

    public void setCompra(String compra) {
        this.compra = compra;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
