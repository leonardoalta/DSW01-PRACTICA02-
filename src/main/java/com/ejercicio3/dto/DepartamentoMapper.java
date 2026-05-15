package com.ejercicio3.dto;

import com.ejercicio3.model.Departamento;
import com.ejercicio3.model.Empleado;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DepartamentoMapper {

    public DepartamentoResponse toResponse(Departamento departamento) {
        DepartamentoResponse response = new DepartamentoResponse();
        response.setId(departamento.getId());
        response.setNombre(departamento.getNombre());
        response.setDescripcion(departamento.getDescripcion());
        return response;
    }

    public DepartamentoResumenResponse toResumenResponse(Departamento departamento, long cantidadEmpleados) {
        DepartamentoResumenResponse response = new DepartamentoResumenResponse();
        response.setId(departamento.getId());
        response.setNombre(departamento.getNombre());
        response.setDescripcion(departamento.getDescripcion());
        response.setCantidadEmpleados(cantidadEmpleados);
        return response;
    }

    public DepartamentoDetalleResponse toDetalleResponse(
            Departamento departamento,
            List<Empleado> empleados,
            long cantidadEmpleados) {
        DepartamentoDetalleResponse response = new DepartamentoDetalleResponse();
        response.setId(departamento.getId());
        response.setNombre(departamento.getNombre());
        response.setDescripcion(departamento.getDescripcion());
        response.setCantidadEmpleados(cantidadEmpleados);
        response.setEmpleados(empleados.stream().map(EmpleadoResumenResponse::from).toList());
        return response;
    }
}
