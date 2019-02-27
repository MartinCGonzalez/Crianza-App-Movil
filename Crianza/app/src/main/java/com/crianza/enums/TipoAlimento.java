package com.crianza.enums;

public enum TipoAlimento {
    CALOSTRO_NATURAL("Calostro Natural",10,2),
    CALOSTRO_FORZADO("Calostro Forzado",11,2),
    LECHE("Leche",20,2),
    SUSTITUTO_LACTEO("Sustituto Lacteo",30,1),
    INICIADOR("Iniciador",40,1),
    RACION("Racion",50,1);

    private String nombre;
    private int numero;
    private int idUnidad;

    private TipoAlimento(String nombre, int numero, int idUnidad) {
        this.nombre = nombre;
        this.numero = numero;
        this.idUnidad = idUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(int idUnidad) {
        this.idUnidad = idUnidad;
    }
}
