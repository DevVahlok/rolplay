package com.example.rolplay.Otros;

public class ItemMontura {

    //Declaración de variables
    private String nombre;
    private int coste;
    private float velocidad;
    private int capacidadCarga;
    private String url;

    public ItemMontura(String nombre, int coste, float velocidad, int capacidadCarga, String url) {
        this.nombre = nombre;
        this.coste = coste;
        this.velocidad = velocidad;
        this.capacidadCarga = capacidadCarga;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCoste() {
        return coste;
    }

    public void setCoste(int coste) {
        this.coste = coste;
    }

    public float getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
