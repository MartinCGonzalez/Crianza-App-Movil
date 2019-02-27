package com.crianza.dtos;

public class EnfermedadDTO {

    private Long idEnfermedad;
    private String nombre;
    private Long gradoGravedad;


    public EnfermedadDTO() {
        super();
    }

    public EnfermedadDTO(Long idEnfermedad, String nombre, Long gradoGravedad) {
        this.idEnfermedad = idEnfermedad;
        this.nombre = nombre;
        this.gradoGravedad = gradoGravedad;
    }

    public Long getIdEnfermedad() {
        return idEnfermedad;
    }

    public void setIdEnfermedad(Long idEnfermedad) {
        this.idEnfermedad = idEnfermedad;
    }

    public Long getGradoGravedad() {
        return gradoGravedad;
    }

    public void setGradoGravedad(Long gradoGravedad) {
        this.gradoGravedad = gradoGravedad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "EnfermedadDTO{" +
                "idEnfermedad=" + idEnfermedad +
                ", gradoGravedad=" + gradoGravedad +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
