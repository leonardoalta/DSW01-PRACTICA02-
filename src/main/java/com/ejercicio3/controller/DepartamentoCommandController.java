package com.ejercicio3.controller;

import com.ejercicio3.dto.AsignacionDepartamentoResponse;
import com.ejercicio3.dto.DepartamentoCreateRequest;
import com.ejercicio3.dto.DepartamentoResponse;
import com.ejercicio3.dto.DepartamentoUpdateRequest;
import com.ejercicio3.service.DepartamentoCommandService;
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
@RequestMapping("${app.api.base-path:/api/v1}/departamentos")
public class DepartamentoCommandController {

    private final DepartamentoCommandService departamentoCommandService;

    public DepartamentoCommandController(DepartamentoCommandService departamentoCommandService) {
        this.departamentoCommandService = departamentoCommandService;
    }

    @PostMapping
    public ResponseEntity<DepartamentoResponse> create(@Valid @RequestBody DepartamentoCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(departamentoCommandService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody DepartamentoUpdateRequest request) {
        return ResponseEntity.ok(departamentoCommandService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        departamentoCommandService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{departamentoId}/empleados/{claveEmpleado}")
    public ResponseEntity<AsignacionDepartamentoResponse> assignEmpleado(
            @PathVariable Long departamentoId,
            @PathVariable String claveEmpleado) {
        return ResponseEntity.ok(departamentoCommandService.assignEmpleado(departamentoId, claveEmpleado));
    }
}
