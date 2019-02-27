package com.crianza.dtos;

import java.math.BigDecimal;

public class TerneroDTO {

    private Long idTernero;
    private Long nroCaravana;
    private Long idGuachera;
    private Long idMadre;
    private Long idPadre;
    private BigDecimal pesoNac;
    private String fechaNac;
    private String parto;
    private String raza;

    public TerneroDTO() {
        super();
    }

    public TerneroDTO(Long idTernero, Long nroCaravana, Long idGuachera) {
        this.idTernero = idTernero;
        this.nroCaravana = nroCaravana;
        this.idGuachera = idGuachera;
    }

    public TerneroDTO(Long nroCaravana, Long idGuachera, Long idMadre, Long idPadre, BigDecimal pesoNac, String fechaNac, String parto, String raza) {
        this.nroCaravana = nroCaravana;
        this.idGuachera = idGuachera;
        this.idMadre = idMadre;
        this.idPadre = idPadre;
        this.pesoNac = pesoNac;
        this.fechaNac = fechaNac;
        this.parto = parto;
        this.raza = raza;
    }

    public Long getIdTernero() {
        return idTernero;
    }

    public void setIdTernero(Long idTernero) {
        this.idTernero = idTernero;
    }

    public Long getNroCaravana() {
        return nroCaravana;
    }

    public void setNroCaravana(Long nroCaravana) {
        this.nroCaravana = nroCaravana;
    }

    public Long getIdGuachera() {
        return idGuachera;
    }

    public void setIdGuachera(Long idGuachera) {
        this.idGuachera = idGuachera;
    }

    public Long getIdMadre() {
        return idMadre;
    }

    public void setIdMadre(Long idMadre) {
        this.idMadre = idMadre;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public BigDecimal getPesoNac() {
        return pesoNac;
    }

    public void setPesoNac(BigDecimal pesoNac) {
        this.pesoNac = pesoNac;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public String getParto() {
        return parto;
    }

    public void setParto(String parto) {
        this.parto = parto;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    @Override
    public String toString() {
        return "TerneroDTO{" +
                "idTernero=" + idTernero +
                ", nroCaravana=" + nroCaravana +
                ", idGuachera=" + idGuachera +
                ", idMadre=" + idMadre +
                ", idPadre=" + idPadre +
                ", pesoNac=" + pesoNac +
                ", fechaNac='" + fechaNac + '\'' +
                ", parto='" + parto + '\'' +
                ", raza='" + raza + '\'' +
                '}';
    }
}
