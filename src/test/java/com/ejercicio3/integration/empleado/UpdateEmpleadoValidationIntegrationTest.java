package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.dto.EmpleadoUpdateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateEmpleadoValidationIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnBadRequestWhenUpdateNombreExceeds100Chars() throws Exception {
                String clave = createEmpleado();

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("A".repeat(101));
        request.setTelefono("5559999");
        request.setEmail("update.validation@ejercicio3.local");

        mockMvc.perform(put("/api/v1/empleados/{clave}", clave)
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateTelefonoIsBlank() throws Exception {
                String clave = createEmpleado();

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("Juan Actualizado");
        request.setTelefono(" ");
        request.setEmail("update.validation@ejercicio3.local");

        mockMvc.perform(put("/api/v1/empleados/{clave}", clave)
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

        @Test
        void shouldReturnBadRequestWhenUpdateContrasenaTooShort() throws Exception {
        String clave = createEmpleado();

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("Juan Actualizado");
        request.setTelefono("5559999");
        request.setEmail("update.validation@ejercicio3.local");
        request.setContrasena("1234567");

                mockMvc.perform(put("/api/v1/empleados/{clave}", clave)
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
        }

        private String createEmpleado() throws Exception {
        EmpleadoCreateRequest createRequest = new EmpleadoCreateRequest();
        createRequest.setNombre("Juan Perez");
        createRequest.setTelefono("5551234");
        createRequest.setEmail("update.validation@ejercicio3.local");
        createRequest.setContrasena("clave1234");

                String body = mockMvc.perform(post("/api/v1/empleados")
                .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                                .andExpect(status().isCreated())
                                .andReturn()
                                .getResponse()
                                .getContentAsString();

                return objectMapper.readTree(body).get("clave").asText();
    }
}
