package com.ejercicio3.service;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.dto.EmpleadoResponse;
import com.ejercicio3.dto.EmpleadoUpdateRequest;
import com.ejercicio3.exception.DuplicateClaveException;
import com.ejercicio3.exception.InvalidStateException;
import com.ejercicio3.exception.ResourceNotFoundException;
import com.ejercicio3.model.Empleado;
import com.ejercicio3.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpleadoCommandService.class);

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoCommandService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Transactional
    public EmpleadoResponse create(EmpleadoCreateRequest request) {
        if (empleadoRepository.existsById(request.getClave())) {
            throw new DuplicateClaveException("La clave ya existe: " + request.getClave());
        }

        Empleado empleado = new Empleado();
        empleado.setClave(request.getClave());
        empleado.setNombre(request.getNombre());
        empleado.setTelefono(request.getTelefono());
        empleado.setActivo(true);

        Empleado saved = empleadoRepository.save(empleado);
        LOGGER.info("empleado_created clave={} activo={}", saved.getClave(), saved.isActivo());
        return EmpleadoResponse.from(saved);
    }

    @Transactional
    public EmpleadoResponse update(String clave, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findByClaveAndActivoTrue(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + clave));

        empleado.setNombre(request.getNombre());
        empleado.setTelefono(request.getTelefono());

        Empleado saved = empleadoRepository.save(empleado);
        LOGGER.info("empleado_updated clave={}", saved.getClave());
        return EmpleadoResponse.from(saved);
    }

    @Transactional
    public void deleteLogically(String clave) {
        Empleado empleado = empleadoRepository.findById(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + clave));

        if (!empleado.isActivo()) {
            throw new InvalidStateException("El empleado ya fue eliminado logicamente: " + clave);
        }

        empleado.setActivo(false);
        empleadoRepository.save(empleado);
        LOGGER.info("empleado_deleted_logically clave={}", clave);
    }
}
