package com.example.rolplay.Otros;

import android.net.Uri;

public class ItemPersonaje {

    //Declaración de variables
    private String nombre;
    private String tipoJuego;
    private String codigo;
    private String foto;

    public ItemPersonaje(String nombre, String tipoJuego, String codigo, String foto) {
        this.nombre = nombre;
        this.tipoJuego = tipoJuego;
        this.codigo = codigo;
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoJuego() {
        return tipoJuego;
    }

    public void setTipoJuego(String tipoJuego) {
        this.tipoJuego = tipoJuego;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
