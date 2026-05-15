package com.ejercicio3.service;

import com.ejercicio3.dto.AsignacionDepartamentoResponse;
import com.ejercicio3.dto.DepartamentoCreateRequest;
import com.ejercicio3.dto.DepartamentoMapper;
import com.ejercicio3.dto.DepartamentoResponse;
import com.ejercicio3.dto.DepartamentoUpdateRequest;
import com.ejercicio3.exception.DepartamentoConflictException;
import com.ejercicio3.exception.ResourceNotFoundException;
import com.ejercicio3.model.Departamento;
import com.ejercicio3.model.Empleado;
import com.ejercicio3.repository.DepartamentoRepository;
import com.ejercicio3.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartamentoCommandService.class);

    private final DepartamentoRepository departamentoRepository;
    private final EmpleadoRepository empleadoRepository;
    private final DepartamentoMapper departamentoMapper;

    public DepartamentoCommandService(
            DepartamentoRepository departamentoRepository,
            EmpleadoRepository empleadoRepository,
            DepartamentoMapper departamentoMapper) {
        this.departamentoRepository = departamentoRepository;
        this.empleadoRepository = empleadoRepository;
        this.departamentoMapper = departamentoMapper;
    }

    @Transactional
    public DepartamentoResponse create(DepartamentoCreateRequest request) {
        if (departamentoRepository.existsByNombreIgnoreCase(request.getNombre())) {
            throw new DepartamentoConflictException("Ya existe un departamento con nombre: " + request.getNombre());
        }

        Departamento departamento = new Departamento();
        departamento.setNombre(request.getNombre().trim());
        departamento.setDescripcion(request.getDescripcion());

        Departamento saved = departamentoRepository.save(departamento);
        LOGGER.info("departamento_created id={} nombre={}", saved.getId(), saved.getNombre());
        return departamentoMapper.toResponse(saved);
    }

    @Transactional
    public DepartamentoResponse update(Long id, DepartamentoUpdateRequest request) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado: " + id));

        if (departamentoRepository.existsByNombreIgnoreCaseAndIdNot(request.getNombre(), id)) {
            throw new DepartamentoConflictException("Ya existe un departamento con nombre: " + request.getNombre());
        }

        departamento.setNombre(request.getNombre().trim());
        departamento.setDescripcion(request.getDescripcion());

        Departamento saved = departamentoRepository.save(departamento);
        LOGGER.info("departamento_updated id={} nombre={}", saved.getId(), saved.getNombre());
        return departamentoMapper.toResponse(saved);
    }

    @Transactional
    public void delete(Long id) {
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado: " + id));

        if (empleadoRepository.existsByDepartamento_IdAndActivoTrue(id)) {
            throw new DepartamentoConflictException(
                    "No se puede eliminar el departamento porque tiene empleados asociados: " + id);
        }

        departamentoRepository.delete(departamento);
        LOGGER.info("departamento_deleted id={} nombre={}", departamento.getId(), departamento.getNombre());
    }

    @Transactional
    public AsignacionDepartamentoResponse assignEmpleado(Long departamentoId, String claveEmpleado) {
        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado: " + departamentoId));

        Empleado empleado = empleadoRepository.findByClaveAndActivoTrue(claveEmpleado)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + claveEmpleado));

        empleado.setDepartamento(departamento);
        empleadoRepository.save(empleado);

        AsignacionDepartamentoResponse response = new AsignacionDepartamentoResponse();
        response.setClaveEmpleado(empleado.getClave());
        response.setDepartamentoId(departamento.getId());
        response.setDepartamentoNombre(departamento.getNombre());

        LOGGER.info("empleado_assigned_to_departamento clave={} departamentoId={}", empleado.getClave(), departamento.getId());
        return response;
    }
}
