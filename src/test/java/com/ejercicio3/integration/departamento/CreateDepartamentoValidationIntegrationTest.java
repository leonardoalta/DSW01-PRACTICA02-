package com.ejercicio3.integration.departamento;

import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateDepartamentoValidationIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnBadRequestWhenNombreIsMissing() throws Exception {
        String body = "{\"descripcion\":\"sin nombre\"}";

        mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenNombreExceedsMaxLength() throws Exception {
        String nombre = "N".repeat(101);
        String body = "{\"nombre\":\"" + nombre + "\"}";

        mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}
