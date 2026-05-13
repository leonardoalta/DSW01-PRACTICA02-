package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoUpdateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateDeleteNotFoundIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnNotFoundForUpdateAndDeleteWhenClaveMissing() throws Exception {
        EmpleadoUpdateRequest updateRequest = new EmpleadoUpdateRequest();
        updateRequest.setNombre("Nombre");
        updateRequest.setTelefono("5550000");
        updateRequest.setEmail("nombre@ejercicio3.local");

        mockMvc.perform(put("/api/v1/empleados/NOPE")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isNotFound());

        mockMvc.perform(delete("/api/v1/empleados/NOPE").with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNotFound());
    }
}
