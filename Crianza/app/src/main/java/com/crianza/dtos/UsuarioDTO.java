package com.crianza.dtos;

public class UsuarioDTO {

    private Long idUsuario;
    private String apellido;
    private String contraseña;
    private String nombre;
    private String perfil;
    private String usuario;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public UsuarioDTO(String usuario, String contraseña) {
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public UsuarioDTO(Long idUsuario, String contraseña) {
        this.idUsuario = idUsuario;
        this.contraseña = contraseña;
    }

    public UsuarioDTO(String usuario, String contraseña, String perfil) {
        this.idUsuario = idUsuario;
        this.contraseña = contraseña;
        this.perfil = perfil;
    }

    public UsuarioDTO(String nombre, String apellido, String perfil, String contraseña) {
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.perfil = perfil;
    }

    public UsuarioDTO(Long idUsuario, String nombre, String apellido, String perfil, String usuario, String contraseña) {
        this.idUsuario = idUsuario;
        this.apellido = apellido;
        this.contraseña = contraseña;
        this.nombre = nombre;
        this.perfil = perfil;
        this.usuario = usuario;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "UsuarioDTO{" +
                "apellido='" + apellido + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", nombre='" + nombre + '\'' +
                ", perfil='" + perfil + '\'' +
                '}';
    }
}
