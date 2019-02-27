package com.crianza.dtos;

import java.math.BigDecimal;

public class AlimentoDTO {

    private Long idAlimento;
    private String nombre;
    private BigDecimal cantidad;
    private BigDecimal costoUnidad;
    private UnidadDTO unidad;
    private String tipoUnidad;

    public AlimentoDTO(Long idAlimento, String nombre, BigDecimal cantidad, BigDecimal costoUnidad, String tipoUnidad) {
        this.idAlimento = idAlimento;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costoUnidad = costoUnidad;
        this.tipoUnidad = tipoUnidad;
    }

    public AlimentoDTO(){
        super();
    }

    public AlimentoDTO(Long idAlimento, String nombre, BigDecimal cantidad, BigDecimal costoUnidad, UnidadDTO unidad) {
        this.idAlimento = idAlimento;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.costoUnidad = costoUnidad;
        this.unidad = unidad;
    }

    public AlimentoDTO(String nombre,BigDecimal costoUnidad , BigDecimal cantidad) {
        super();
        this.nombre = nombre;
        this.costoUnidad = costoUnidad;
        this.cantidad = cantidad;
    }

    public AlimentoDTO(BigDecimal cantidad, String nombre) {
        super();
        this.cantidad = cantidad;
        this.nombre = nombre;
    }

    public AlimentoDTO(String nombre) {
        super();
        this.nombre = nombre;
    }

    public AlimentoDTO(Long idAlimento, BigDecimal cantidad, BigDecimal costoUnidad) {
        super();
        this.idAlimento = idAlimento;
        this.cantidad = cantidad;
        this.costoUnidad = costoUnidad;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoUnidad() {
        return costoUnidad;
    }

    public void setCostoUnidad(BigDecimal costoUnidad) {
        this.costoUnidad = costoUnidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Long idAlimento) {
        this.idAlimento = idAlimento;
    }

    public UnidadDTO getUnidad() {
        return unidad;
    }

    public void setUnidad(UnidadDTO unidad) {
        this.unidad = unidad;
    }

    public String getTipoUnidad() {
        return tipoUnidad;
    }

    public void setTipoUnidad(String tipoUnidad) {
        this.tipoUnidad = tipoUnidad;
    }

    @Override
    public String toString() {
        return "AlimentoDTO{" +
                "idAlimento=" + idAlimento +
                ", nombre='" + nombre + '\'' +
                ", cantidad=" + cantidad +
                ", costoUnidad=" + costoUnidad +
                ", unidad='" + unidad + '\'' +
                '}';
    }
}
