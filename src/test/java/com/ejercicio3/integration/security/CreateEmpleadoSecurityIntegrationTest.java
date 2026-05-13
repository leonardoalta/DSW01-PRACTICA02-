package com.ejercicio3.integration.security;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateEmpleadoSecurityIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnUnauthorizedWithoutCredentials() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Juan Perez");
        request.setTelefono("5551234");
        request.setEmail("juan.perez@ejercicio3.local");
        request.setContrasena("clave1234");

        mockMvc.perform(post("/api/v1/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }
}
