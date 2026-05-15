package com.ejercicio3.integration.departamento;

import com.ejercicio3.dto.DepartamentoCreateRequest;
import com.ejercicio3.dto.DepartamentoUpdateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateDepartamentoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldUpdateDepartamentoSuccessfully() throws Exception {
        DepartamentoCreateRequest createRequest = new DepartamentoCreateRequest();
        createRequest.setNombre("Operaciones");
        createRequest.setDescripcion("Area operativa");

        MvcResult createResult = mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        long id = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

        DepartamentoUpdateRequest updateRequest = new DepartamentoUpdateRequest();
        updateRequest.setNombre("Operaciones Regionales");
        updateRequest.setDescripcion("Area operativa actualizada");

        mockMvc.perform(put("/api/v1/departamentos/{id}", id)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.nombre").value("Operaciones Regionales"))
                .andExpect(jsonPath("$.descripcion").value("Area operativa actualizada"));
    }
}
