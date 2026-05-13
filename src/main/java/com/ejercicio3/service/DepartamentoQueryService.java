package com.ejercicio3.service;

import com.ejercicio3.dto.DepartamentoDetalleResponse;
import com.ejercicio3.dto.DepartamentoMapper;
import com.ejercicio3.dto.DepartamentoResumenResponse;
import com.ejercicio3.exception.ResourceNotFoundException;
import com.ejercicio3.model.Departamento;
import com.ejercicio3.model.Empleado;
import com.ejercicio3.repository.DepartamentoRepository;
import com.ejercicio3.repository.EmpleadoRepository;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DepartamentoQueryService {

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoMapper departamentoMapper;

    public DepartamentoQueryService(
            DepartamentoRepository departamentoRepository,
            EmpleadoRepository empleadoRepository,
            DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.empleadoRepository = empleadoRepository;
        this.departamentoMapper = departamentoMapper;
    }

    public List<DepartamentoResumenResponse> list() {
        return departamentoRepository.findAll(Sort.by("id").ascending()).stream()
                .map(departamento -> departamentoMapper.toResumenResponse(
                        departamento,
                        empleadoRepository.countByDepartamento_IdAndActivoTrue(departamento.getId())))
                .toList();
    }

    public DepartamentoDetalleResponse getById(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado: " + id));

        List<Empleado> empleados = empleadoRepository.findByDepartamento_IdAndActivoTrueOrderByClaveAsc(id);
        return departamentoMapper.toDetalleResponse(departamento, empleados, empleados.size());
    }
}
