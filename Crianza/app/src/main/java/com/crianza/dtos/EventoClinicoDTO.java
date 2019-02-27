package com.crianza.dtos;

public class EventoClinicoDTO {

    private Long idEnfermedad;
    private Long idTernero;
    private String fechaDesde;
    private String fechaHasta;
    private String fechaNac;
    private Long diasVida;
    private String nombreEnfermedad;
    private Long gravedadEnfermedad;
    private String observacion;

    public EventoClinicoDTO() {
        super();
    }

    public EventoClinicoDTO(Long idEnfermedad, Long idTernero, String fechaDesde, String observacion) {
        super();
        this.idEnfermedad = idEnfermedad;
        this.idTernero = idTernero;
        this.fechaDesde = fechaDesde;
        this.observacion = observacion;
    }

    public EventoClinicoDTO(Long idEnfermedad, Long idTernero, String fechaDesde, String fechaHasta, String observacion) {
        super();
        this.idEnfermedad = idEnfermedad;
        this.idTernero = idTernero;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.observacion = observacion;
    }

    public EventoClinicoDTO(Long idTernero, String fechaNac, Long diasVida, String fechaDesde, String fechaHasta, String nombreEnfermedad, Long gravedadEnfermedad) {
        this.idTernero = idTernero;
        this.fechaDesde = fechaDesde;
        this.fechaHasta = fechaHasta;
        this.fechaNac = fechaNac;
        this.diasVida = diasVida;
        this.nombreEnfermedad = nombreEnfermedad;
        this.gravedadEnfermedad = gravedadEnfermedad;
    }


    public Long getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(Long idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public Long getIdTernero() {
        return idTernero;
    }

    public void setIdTernero(Long idTernero) {
        this.idTernero = idTernero;
    }

    public String getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(String fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Long getDiasVida() {
        return diasVida;
    }

    public void setDiasVida(Long diasVida) {
        this.diasVida = diasVida;
    }

    public String getNombreEnfermedad() {
        return nombreEnfermedad;
    }

    public void setNombreEnfermedad(String nombreEnfermedad) {
        this.nombreEnfermedad = nombreEnfermedad;
    }

    public Long getGravedadEnfermedad() {
        return gravedadEnfermedad;
    }

    public void setGravedadEnfermedad(Long gravedadEnfermedad) {
        this.gravedadEnfermedad = gravedadEnfermedad;
    }

    @Override
    public String toString() {
        return "EventoClinicoDTO{" +
                ", idEnfermedad=" + idEnfermedad +
                ", idTernero=" + idTernero +
                ", fechaDesde='" + fechaDesde + '\'' +
                ", fechaHasta='" + fechaHasta + '\'' +
                ", observacion='" + observacion + '\'' +
                '}';
    }
}
