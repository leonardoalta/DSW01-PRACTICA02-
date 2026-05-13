package com.ejercicio3.controller;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.dto.EmpleadoResponse;
import com.ejercicio3.dto.EmpleadoUpdateRequest;
import com.ejercicio3.service.EmpleadoCommandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.base-path:/api/v1}/empleados")
public class EmpleadoCommandController {

    private final EmpleadoCommandService empleadoCommandService;

    public EmpleadoCommandController(EmpleadoCommandService empleadoCommandService) {
        this.empleadoCommandService = empleadoCommandService;
    }

    @PostMapping
    public ResponseEntity<EmpleadoResponse> create(@Valid @RequestBody EmpleadoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empleadoCommandService.create(request));
    }

    @PutMapping("/{clave}")
    public ResponseEntity<EmpleadoResponse> update(
            @PathVariable String clave,
            @Valid @RequestBody EmpleadoUpdateRequest request) {
        return ResponseEntity.ok(empleadoCommandService.update(clave, request));
    }

    @DeleteMapping("/{clave}")
    public ResponseEntity<Void> delete(@PathVariable String clave) {
        empleadoCommandService.deleteLogically(clave);
        return ResponseEntity.noContent().build();
    }
}
