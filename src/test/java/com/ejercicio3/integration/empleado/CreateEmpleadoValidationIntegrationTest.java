package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateEmpleadoValidationIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnBadRequestWhenNombreExceeds100Chars() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("A".repeat(101));
        request.setTelefono("5551234");
        request.setEmail("nombre.largo@ejercicio3.local");
        request.setContrasena("clave1234");

        mockMvc.perform(post("/api/v1/empleados")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
        }

        @Test
        void shouldReturnBadRequestWhenTelefonoExceeds100Chars() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Juan Perez");
        request.setTelefono("1".repeat(101));
        request.setEmail("telefono.largo@ejercicio3.local");
        request.setContrasena("clave1234");

        mockMvc.perform(post("/api/v1/empleados")
            .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
        }

        @Test
        void shouldIgnoreManualClaveSentInPayload() throws Exception {
        String payload = """
                {
                  \"clave\": \"EMP-MANUAL\",
                  \"nombre\": \"Juan Perez\",
                  \"telefono\": \"5551234\",
                                    \"email\": \"manual.payload@ejercicio3.local\",
                  \"contrasena\": \"clave1234\"
                }
                """;

        mockMvc.perform(post("/api/v1/empleados")
            .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.clave").isNotEmpty());
    }

        @Test
        void shouldReturnBadRequestWhenContrasenaIsTooShort() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Juan Perez");
        request.setTelefono("5551234");
        request.setEmail("contrasena.corta@ejercicio3.local");
        request.setContrasena("1234567");

        mockMvc.perform(post("/api/v1/empleados")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
        }
}
