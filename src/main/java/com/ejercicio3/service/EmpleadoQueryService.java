package com.ejercicio3.service;

import com.ejercicio3.dto.EmpleadoPageResponse;
import com.ejercicio3.dto.EmpleadoResponse;
import com.ejercicio3.exception.BadRequestException;
import com.ejercicio3.exception.ResourceNotFoundException;
import com.ejercicio3.model.Empleado;
import com.ejercicio3.repository.EmpleadoRepository;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoQueryService {

    private static final int PAGE_SIZE = 5;

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoQueryService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Object list(Integer page) {
        if (page == null) {
            return empleadoRepository.findByActivoTrueOrderByClaveAsc().stream()
                    .map(EmpleadoResponse::from)
                    .toList();
        }

        if (page < 0) {
            throw new BadRequestException("El parametro page debe ser un entero mayor o igual a 0");
        }

        Page<Empleado> paged = empleadoRepository.findByActivoTrue(
                PageRequest.of(page, PAGE_SIZE, Sort.by("clave").ascending()));

        EmpleadoPageResponse response = new EmpleadoPageResponse();
        response.setContent(paged.getContent().stream().map(EmpleadoResponse::from).toList());
        response.setPage(paged.getNumber());
        response.setSize(paged.getSize());
        response.setTotalElements(paged.getTotalElements());
        response.setTotalPages(paged.getTotalPages());
        return response;
    }

    public EmpleadoResponse getByClave(String clave) {
        Empleado empleado = empleadoRepository.findByClaveAndActivoTrue(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + clave));
        return EmpleadoResponse.from(empleado);
    }
}
