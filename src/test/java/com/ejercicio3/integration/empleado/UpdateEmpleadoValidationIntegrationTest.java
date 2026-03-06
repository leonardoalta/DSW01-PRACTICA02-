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
        createEmpleado("EMP001");

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("A".repeat(101));
        request.setTelefono("5559999");

        mockMvc.perform(put("/api/empleados/EMP001")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    void shouldReturnBadRequestWhenUpdateTelefonoIsBlank() throws Exception {
        createEmpleado("EMP002");

        EmpleadoUpdateRequest request = new EmpleadoUpdateRequest();
        request.setNombre("Juan Actualizado");
        request.setTelefono(" ");

        mockMvc.perform(put("/api/empleados/EMP002")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    private void createEmpleado(String clave) throws Exception {
        EmpleadoCreateRequest createRequest = new EmpleadoCreateRequest();
        createRequest.setClave(clave);
        createRequest.setNombre("Juan Perez");
        createRequest.setTelefono("5551234");

        mockMvc.perform(post("/api/empleados")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());
    }
}
