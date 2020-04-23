package com.example.rolplay.Otros;

public class ItemAtaque {

    //Declaración de variables
    private String nombre;
    private int coste;
    private int peso;
    private String url;
    private String danyo;
    private String propiedades;

    public ItemAtaque(String nombre, int coste, int peso, String url, String danyo, String propiedades) {
        this.nombre = nombre;
        this.coste = coste;
        this.peso = peso;
        this.url = url;
        this.danyo = danyo;
        this.propiedades = propiedades;
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

    public String getDanyo() {
        return danyo;
    }

    public void setDanyo(String danyo) {
        this.danyo = danyo;
    }

    public String getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(String propiedades) {
        this.propiedades = propiedades;
    }

}
