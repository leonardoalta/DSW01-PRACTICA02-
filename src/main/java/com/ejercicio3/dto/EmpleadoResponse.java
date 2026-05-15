package com.ejercicio3.dto;

import com.ejercicio3.model.Empleado;

public class EmpleadoResponse {

    private String clave;
    private String nombre;
    private String telefono;
    private String email;
    private boolean activo;
    private Long departamentoId;

    public static EmpleadoResponse from(Empleado empleado) {
        EmpleadoResponse response = new EmpleadoResponse();
        response.setClave(empleado.getClave());
        response.setNombre(empleado.getNombre());
        response.setTelefono(empleado.getTelefono());
        response.setEmail(empleado.getEmail());
        response.setActivo(empleado.isActivo());
        response.setDepartamentoId(empleado.getDepartamento() != null ? empleado.getDepartamento().getId() : null);
        return response;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }
}
