package com.ejercicio3.integration;

import com.ejercicio3.model.Empleado;
import com.ejercicio3.model.MasterAccessConfig;
import com.ejercicio3.repository.DepartamentoRepository;
import com.ejercicio3.repository.EmpleadoRepository;
import com.ejercicio3.repository.MasterAccessConfigRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    protected static final String AUTH_USER = "master@ejercicio3.local";
    protected static final String AUTH_CLAVE = "AUTH_EMP";
    protected static final String AUTH_PASSWORD = "admin123";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected EmpleadoRepository empleadoRepository;

    @Autowired
    protected DepartamentoRepository departamentoRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected MasterAccessConfigRepository masterAccessConfigRepository;

    @BeforeEach
    void cleanup() {
        empleadoRepository.deleteAll();
        departamentoRepository.deleteAll();

        Empleado authEmpleado = new Empleado();
        authEmpleado.setClave(AUTH_CLAVE);
        authEmpleado.setNombre("Empleado Auth");
        authEmpleado.setTelefono("0000000000");
        authEmpleado.setEmail(AUTH_USER);
        authEmpleado.setContrasenaHash(passwordEncoder.encode(AUTH_PASSWORD));
        authEmpleado.setActivo(true);
        empleadoRepository.save(authEmpleado);

        MasterAccessConfig masterConfig = new MasterAccessConfig();
        masterConfig.setId("MASTER");
        masterConfig.setMasterPrincipal(AUTH_USER);
        masterConfig.setEnabled(true);
        masterAccessConfigRepository.save(masterConfig);
    }
}
