package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateEmpleadoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateEmpleadoSuccessfully() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Juan Perez");
        request.setTelefono("5551234");
        request.setEmail("juan.perez@ejercicio3.local");
        request.setContrasena("clave1234");

        mockMvc.perform(post("/api/v1/empleados")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.clave").isNotEmpty())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.telefono").value("5551234"))
                    .andExpect(jsonPath("$.email").value("juan.perez@ejercicio3.local"))
                .andExpect(jsonPath("$.activo").value(true));
    }
}
