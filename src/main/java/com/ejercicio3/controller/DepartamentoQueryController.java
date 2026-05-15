package com.ejercicio3.controller;

import com.ejercicio3.dto.DepartamentoDetalleResponse;
import com.ejercicio3.dto.DepartamentoResumenResponse;
import com.ejercicio3.service.DepartamentoQueryService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${app.api.base-path:/api/v1}/departamentos")
public class DepartamentoQueryController {

    private final DepartamentoQueryService departamentoQueryService;

    public DepartamentoQueryController(DepartamentoQueryService departamentoQueryService) {
        this.departamentoQueryService = departamentoQueryService;
    }

    @GetMapping
    public ResponseEntity<List<DepartamentoResumenResponse>> list() {
        return ResponseEntity.ok(departamentoQueryService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDetalleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(departamentoQueryService.getById(id));
    }
}
