package com.ejercicio3.service;

import com.ejercicio3.repository.EmpleadoRepository;
import java.util.Locale;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoClaveGenerator {

    private static final String PREFIX = "EMP-";
    private static final int MAX_ATTEMPTS = 10;

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoClaveGenerator(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public String generateUniqueClave() {
        for (int attempt = 0; attempt < MAX_ATTEMPTS; attempt++) {
            String clave = PREFIX + UUID.randomUUID().toString().replace("-", "")
                    .substring(0, 10)
                    .toUpperCase(Locale.ROOT);
            if (!empleadoRepository.existsById(clave)) {
                return clave;
            }
        }

        throw new IllegalStateException("No se pudo generar una clave unica para empleado");
    }
}