package com.ejercicio3.integration.security;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EmpleadoAuthenticationIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldAuthenticateWithActiveEmpleadoCredentials() throws Exception {
        mockMvc.perform(get("/api/v1/empleados").with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldRejectInvalidEmpleadoPassword() throws Exception {
        mockMvc.perform(get("/api/v1/empleados").with(httpBasic(AUTH_USER, "bad-password")))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldAuthenticateWithGeneratedEmpleadoCredentials() throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre("Empleado Login");
        request.setTelefono("5557890");
        request.setEmail("empleado.login@ejercicio3.local");
        request.setContrasena("Password123");

        MvcResult createResult = mockMvc.perform(post("/api/v1/empleados")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        String email = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("email").asText();

        mockMvc.perform(get("/api/v1/empleados").with(httpBasic(email, "Password123")))
                .andExpect(status().isOk());
    }
}
