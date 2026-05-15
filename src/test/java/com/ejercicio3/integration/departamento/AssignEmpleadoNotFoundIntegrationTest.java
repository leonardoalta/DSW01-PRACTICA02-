package com.ejercicio3.integration.departamento;

import com.ejercicio3.dto.DepartamentoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssignEmpleadoNotFoundIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnNotFoundWhenDepartamentoDoesNotExist() throws Exception {
        mockMvc.perform(put("/api/v1/departamentos/{departamentoId}/empleados/{claveEmpleado}", 9999, AUTH_CLAVE)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnNotFoundWhenEmpleadoDoesNotExist() throws Exception {
        long depId = createDepartamento("Planeacion", "Area de planeacion");

        mockMvc.perform(put("/api/v1/departamentos/{departamentoId}/empleados/{claveEmpleado}", depId, "NO_EMP")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNotFound());
    }

    private long createDepartamento(String nombre, String descripcion) throws Exception {
        DepartamentoCreateRequest request = new DepartamentoCreateRequest();
        request.setNombre(nombre);
        request.setDescripcion(descripcion);

        MvcResult result = mockMvc.perform(post("/api/v1/departamentos")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }
}
