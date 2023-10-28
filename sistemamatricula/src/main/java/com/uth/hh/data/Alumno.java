package com.uth.hh.data;

public class Alumno {

    private String numerocuenta;
    private String nombre;
    private String apellido;
    private String genero;
    private String email;
    private String telefono;
    private Integer campus;

    public String getNumerocuenta() {
        return numerocuenta;
    }
    public void setNumerocuenta(String numerocuenta) {
        this.numerocuenta = numerocuenta;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    public Integer getCampus() {
        return campus;
    }
    public void setCampus(Integer campus) {
        this.campus = campus;
    }

}
