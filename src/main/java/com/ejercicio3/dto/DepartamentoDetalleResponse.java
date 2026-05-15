package com.ejercicio3.dto;

import java.util.List;

public class DepartamentoDetalleResponse {

    private Long id;
    private String nombre;
    private String descripcion;
    private long cantidadEmpleados;
    private List<EmpleadoResumenResponse> empleados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getCantidadEmpleados() {
        return cantidadEmpleados;
    }

    public void setCantidadEmpleados(long cantidadEmpleados) {
        this.cantidadEmpleados = cantidadEmpleados;
    }

    public List<EmpleadoResumenResponse> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoResumenResponse> empleados) {
        this.empleados = empleados;
    }
}
