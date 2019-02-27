package com.crianza.dtos;

import java.math.BigDecimal;

public class PesoDTO {

    private String fecha;
    private BigDecimal peso;
    private String tipoRegistro;
    private Long idTernero;
    private BigDecimal ganancia;

    public PesoDTO() {
        super();
    }

    public PesoDTO(String fecha, BigDecimal peso, String tipoRegistro, Long idTernero) {
        super();
        this.fecha = fecha;
        this.peso = peso;
        this.tipoRegistro = tipoRegistro;
        this.idTernero = idTernero;
        this.ganancia = ganancia;
    }

    public PesoDTO(Long idTernero, String fecha, BigDecimal peso,  BigDecimal ganancia, String tipoRegistro) {
        super();
        this.idTernero = idTernero;
        this.fecha = fecha;
        this.peso = peso;
        this.ganancia = ganancia;
        this.tipoRegistro = tipoRegistro;

    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getPeso() {
        return peso;
    }

    public void setPeso(BigDecimal peso) {
        this.peso = peso;
    }

    public String getTipoRegistro() {
        return tipoRegistro;
    }

    public void setTipoRegistro(String tipoRegistro) {
        this.tipoRegistro = tipoRegistro;
    }

    public Long getIdTernero() {
        return idTernero;
    }

    public void setIdTernero(Long idTernero) {
        this.idTernero = idTernero;
    }

    public BigDecimal getGanancia() {
        return ganancia;
    }

    public void setGanancia(BigDecimal ganancia) {
        this.ganancia = ganancia;
    }

    @Override
    public String toString() {
        return "PesoDTO{" +
                "fecha='" + fecha + '\'' +
                ", peso=" + peso +
                ", tipoRegistro='" + tipoRegistro + '\'' +
                ", idTernero=" + idTernero +
                ", ganancia=" + ganancia +
                '}';
    }
}
