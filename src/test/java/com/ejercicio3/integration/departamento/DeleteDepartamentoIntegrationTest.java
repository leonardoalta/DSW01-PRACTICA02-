package com.ejercicio3.integration.departamento;

import com.ejercicio3.dto.DepartamentoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteDepartamentoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldDeleteDepartamentoWithoutAssociatedEmpleados() throws Exception {
        DepartamentoCreateRequest request = new DepartamentoCreateRequest();
        request.setNombre("Legal");

        MvcResult createResult = mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        mockMvc.perform(delete("/api/v1/departamentos/{id}", id)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/departamentos/{id}", id)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNotFound());
    }
}
