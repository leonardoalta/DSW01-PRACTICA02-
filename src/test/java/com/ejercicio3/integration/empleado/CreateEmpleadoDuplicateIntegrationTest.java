package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CreateEmpleadoDuplicateIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldGenerateDifferentClaveForDifferentEmployees() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Juan Perez");
        request.setTelefono("5551234");
        request.setEmail("juan.perez@ejercicio3.local");
        request.setContrasena("clave1234");

        MvcResult firstResult = mockMvc.perform(post("/api/v1/empleados")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String firstClave = objectMapper.readTree(firstResult.getResponse().getContentAsString()).get("clave").asText();

        request.setNombre("Ana Perez");
        request.setEmail("ana.perez@ejercicio3.local");

        MvcResult secondResult = mockMvc.perform(post("/api/v1/empleados")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String secondClave = objectMapper.readTree(secondResult.getResponse().getContentAsString()).get("clave").asText();
        Assertions.assertNotEquals(firstClave, secondClave);
    }
}
