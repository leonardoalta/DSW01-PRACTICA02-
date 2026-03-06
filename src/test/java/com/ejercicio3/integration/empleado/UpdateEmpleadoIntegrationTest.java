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

class UpdateEmpleadoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldUpdateEmpleadoSuccessfully() throws Exception {
        EmpleadoCreateRequest createRequest = new EmpleadoCreateRequest();
        createRequest.setClave("EMP001");
        createRequest.setNombre("Juan Perez");
        createRequest.setTelefono("5551234");

        mockMvc.perform(post("/api/empleados")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated());

        EmpleadoUpdateRequest updateRequest = new EmpleadoUpdateRequest();
        updateRequest.setNombre("Juan Actualizado");
        updateRequest.setTelefono("5559999");

        mockMvc.perform(put("/api/empleados/EMP001")
                        .with(httpBasic("admin", "admin123"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Actualizado"))
                .andExpect(jsonPath("$.telefono").value("5559999"));
    }
}
