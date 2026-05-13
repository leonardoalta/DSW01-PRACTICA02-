package com.ejercicio3.service;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.dto.EmpleadoResponse;
import com.ejercicio3.dto.EmpleadoUpdateRequest;
import com.ejercicio3.exception.InvalidStateException;
import com.ejercicio3.exception.MasterAuthorizationException;
import com.ejercicio3.exception.ResourceNotFoundException;
import com.ejercicio3.model.Empleado;
import com.ejercicio3.repository.EmpleadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpleadoCommandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmpleadoCommandService.class);

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoClaveGenerator empleadoClaveGenerator;
    private final PasswordEncoder passwordEncoder;
    private final MasterAccessService masterAccessService;

    public EmpleadoCommandService(
            EmpleadoRepository empleadoRepository,
            EmpleadoClaveGenerator empleadoClaveGenerator,
            PasswordEncoder passwordEncoder,
            MasterAccessService masterAccessService) {
        this.empleadoRepository = empleadoRepository;
        this.empleadoClaveGenerator = empleadoClaveGenerator;
        this.passwordEncoder = passwordEncoder;
        this.masterAccessService = masterAccessService;
    }

    @Transactional
    public EmpleadoResponse create(EmpleadoCreateRequest request) {
        assertMasterWriteAccess();
        validateEmailForCreate(request.getEmail());

        Empleado empleado = new Empleado();
        empleado.setClave(empleadoClaveGenerator.generateUniqueClave());
        empleado.setNombre(request.getNombre());
        empleado.setTelefono(request.getTelefono());
        empleado.setEmail(request.getEmail().trim().toLowerCase());
        empleado.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));
        empleado.setActivo(true);

        Empleado saved = empleadoRepository.save(empleado);
        LOGGER.info("empleado_created clave={} email={} activo={}", saved.getClave(), saved.getEmail(), saved.isActivo());
        return EmpleadoResponse.from(saved);
    }

    @Transactional
    public EmpleadoResponse update(String clave, EmpleadoUpdateRequest request) {
        assertMasterWriteAccess();

        Empleado empleado = empleadoRepository.findByClaveAndActivoTrue(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + clave));

        validateEmailForUpdate(request.getEmail(), clave);

        empleado.setNombre(request.getNombre());
        empleado.setTelefono(request.getTelefono());
        empleado.setEmail(request.getEmail().trim().toLowerCase());
        if (request.getContrasena() != null) {
            empleado.setContrasenaHash(passwordEncoder.encode(request.getContrasena()));
        }

        Empleado saved = empleadoRepository.save(empleado);
        LOGGER.info("empleado_updated clave={} email={}", saved.getClave(), saved.getEmail());
        return EmpleadoResponse.from(saved);
    }

    @Transactional
    public void deleteLogically(String clave) {
        assertMasterWriteAccess();

        Empleado empleado = empleadoRepository.findById(clave)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado: " + clave));

        if (!empleado.isActivo()) {
            throw new InvalidStateException("El empleado ya fue eliminado logicamente: " + clave);
        }

        empleado.setActivo(false);
        empleadoRepository.save(empleado);
        LOGGER.info("empleado_deleted_logically clave={} email={}", clave, empleado.getEmail());
    }

    private void validateEmailForCreate(String email) {
        if (empleadoRepository.existsByEmailIgnoreCase(email)) {
            throw new InvalidStateException("El email ya se encuentra registrado: " + email);
        }
    }

    private void validateEmailForUpdate(String email, String clave) {
        if (empleadoRepository.existsByEmailIgnoreCaseAndClaveNot(email, clave)) {
            throw new InvalidStateException("El email ya se encuentra registrado: " + email);
        }
    }

    private void assertMasterWriteAccess() {
        String currentPrincipal = SecurityContextHolder.getContext().getAuthentication() != null
                ? SecurityContextHolder.getContext().getAuthentication().getName()
                : null;
        if (!masterAccessService.isMaster(currentPrincipal)) {
            throw new MasterAuthorizationException("Solo el usuario master puede ejecutar operaciones de escritura");
        }
    }
}
