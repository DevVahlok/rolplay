package com.example.rolplay;

public class ItemEquipo {

    //Declaración de variables
    private String nombre;
    private int coste;
    private int peso;
    private String url;
    //TODO: Hay que considerar cambiar String url por URI url si es necesario

    public ItemEquipo(String nombre, int coste, int peso, String url) {
        this.nombre = nombre;
        this.coste = coste;
        this.peso = peso;
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

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
