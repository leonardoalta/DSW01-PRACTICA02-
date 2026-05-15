package com.ejercicio3.dto;

import com.ejercicio3.model.Empleado;

public class EmpleadoResumenResponse {

    private String clave;
    private String nombre;
    private String telefono;

    public static EmpleadoResumenResponse from(Empleado empleado) {
        EmpleadoResumenResponse response = new EmpleadoResumenResponse();
        response.setClave(empleado.getClave());
        response.setNombre(empleado.getNombre());
        response.setTelefono(empleado.getTelefono());
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
}
