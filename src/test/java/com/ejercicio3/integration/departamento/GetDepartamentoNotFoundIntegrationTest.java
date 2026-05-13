package com.ejercicio3.integration.departamento;

import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class GetDepartamentoNotFoundIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnNotFoundWhenDepartamentoDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/v1/departamentos/{id}", 9999)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNotFound());
    }
}
