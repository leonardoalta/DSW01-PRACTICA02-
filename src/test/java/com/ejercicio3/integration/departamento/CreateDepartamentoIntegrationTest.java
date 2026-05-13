package com.ejercicio3.integration.departamento;

import com.ejercicio3.dto.DepartamentoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateDepartamentoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldCreateDepartamentoSuccessfully() throws Exception {
        DepartamentoCreateRequest request = new DepartamentoCreateRequest();
        request.setNombre("Finanzas");
        request.setDescripcion("Area financiera");

        mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nombre").value("Finanzas"))
                .andExpect(jsonPath("$.descripcion").value("Area financiera"));
    }
}
