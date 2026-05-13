package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.dto.EmpleadoUpdateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateEmpleadoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldUpdateEmpleadoSuccessfully() throws Exception {
        EmpleadoCreateRequest createRequest = new EmpleadoCreateRequest();
        createRequest.setNombre("Juan Perez");
        createRequest.setTelefono("5551234");
        createRequest.setEmail("update.ok@ejercicio3.local");
        createRequest.setContrasena("clave1234");

        MvcResult createResult = mockMvc.perform(post("/api/v1/empleados")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andReturn();

        String clave = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("clave").asText();

        EmpleadoUpdateRequest updateRequest = new EmpleadoUpdateRequest();
        updateRequest.setNombre("Juan Actualizado");
        updateRequest.setTelefono("5559999");
        updateRequest.setEmail("update.ok@ejercicio3.local");

        mockMvc.perform(put("/api/v1/empleados/{clave}", clave)
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"))
                .andExpect(jsonPath("$.telefono").value("5559999"));
    }
}
