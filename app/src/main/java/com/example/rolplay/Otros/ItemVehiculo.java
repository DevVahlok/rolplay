package com.example.rolplay.Otros;

public class ItemVehiculo {

    //Declaración de variables
    private String nombre;
    private int coste;
    private float velocidad;
    private String url;

    public ItemVehiculo(String nombre, int coste, float velocidad, String url) {
        this.nombre = nombre;
        this.coste = coste;
        this.velocidad = velocidad;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
