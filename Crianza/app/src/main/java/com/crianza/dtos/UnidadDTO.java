package com.crianza.dtos;

public class UnidadDTO {

    private String tipoUnidad;

    public UnidadDTO(String tipoUnidad) {
        super();
        this.tipoUnidad = tipoUnidad;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    @Override
    public String toString() {
        return "UnidadDTO{" +
                "tipoUnidad='" + tipoUnidad + '\'' +
                '}';
    }
}
