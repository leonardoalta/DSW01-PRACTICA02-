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
        request.setClave("EMP001");
        request.setNombre("A".repeat(101));
        request.setTelefono("5551234");

        mockMvc.perform(post("/api/empleados")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
        }

        @Test
        void shouldReturnBadRequestWhenTelefonoExceeds100Chars() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setClave("EMP001");
        request.setNombre("Juan Perez");
        request.setTelefono("1".repeat(101));

        mockMvc.perform(post("/api/empleados")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
        }

        @Test
        void shouldReturnBadRequestWhenClaveIsBlank() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setClave(" ");
        request.setNombre("Juan Perez");
        request.setTelefono("5551234");

        mockMvc.perform(post("/api/empleados")
                .with(httpBasic("admin", "admin123"))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }
}
