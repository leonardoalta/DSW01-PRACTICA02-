package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteEmpleadoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldDeleteEmpleadoLogicallySuccessfully() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Juan Perez");
        request.setTelefono("5551234");
        request.setEmail("delete.ok@ejercicio3.local");
        request.setContrasena("clave1234");

        MvcResult createResult = mockMvc.perform(post("/api/v1/empleados")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String clave = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("clave").asText();

        mockMvc.perform(delete("/api/v1/empleados/{clave}", clave).with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/v1/empleados/{clave}", clave).with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isNotFound());
    }
}
