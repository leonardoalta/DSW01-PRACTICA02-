package com.ejercicio3.controller;

import com.ejercicio3.dto.EmpleadoResponse;
import com.ejercicio3.service.EmpleadoQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoQueryController {

    private final EmpleadoQueryService empleadoQueryService;

    public EmpleadoQueryController(EmpleadoQueryService empleadoQueryService) {
        this.empleadoQueryService = empleadoQueryService;
    }

    @GetMapping
    public ResponseEntity<Object> list(@RequestParam(required = false) Integer page) {
        return ResponseEntity.ok(empleadoQueryService.list(page));
    }

    @GetMapping("/{clave}")
    public ResponseEntity<EmpleadoResponse> getByClave(@PathVariable String clave) {
        return ResponseEntity.ok(empleadoQueryService.getByClave(clave));
    }
}
