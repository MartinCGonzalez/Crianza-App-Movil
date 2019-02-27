package com.crianza.dtos;

import java.math.BigDecimal;
import java.util.Date;

public class ConsumoAlimentoDTO {

    private Long idTernero;
    private Long idAlimento;
    private BigDecimal cantidad;
    private String fecha;

    private Long nroCaravana;
    private Date fecha1;
    private String alimento;
    private String unidad;

    public ConsumoAlimentoDTO() {
        super();
    }

    public ConsumoAlimentoDTO(Long idTernero, Long idAlimento, BigDecimal cantidad, String fecha) {
        super();
        this.idTernero = idTernero;
        this.idAlimento = idAlimento;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

    public ConsumoAlimentoDTO(Long idTernero, BigDecimal cantidad, String fecha, Long nroCaravana, String alimento, String unidad) {
        this.idTernero = idTernero;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.nroCaravana = nroCaravana;
        this.alimento = alimento;
        this.unidad = unidad;
    }

    public Long getIdTernero() {
        return idTernero;
    }

    public void setIdTernero(Long idTernero) {
        this.idTernero = idTernero;
    }

    public Long getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(Long idAlimento) {
        this.idAlimento = idAlimento;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getNroCaravana() {
        return nroCaravana;
    }

    public void setNroCaravana(Long nroCaravana) {
        this.nroCaravana = nroCaravana;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
    }

    public String getAlimento() {
        return alimento;
    }

    public void setAlimento(String alimento) {
        this.alimento = alimento;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    @Override
    public String toString() {
        return "ConsumoAlimentoDTO{" +
                "idTernero=" + idTernero +
                ", idAlimento=" + idAlimento +
                ", cantidad=" + cantidad +
                ", fecha=" + fecha ;
    }
}
