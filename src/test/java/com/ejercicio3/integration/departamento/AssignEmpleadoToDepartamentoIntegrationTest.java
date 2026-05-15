package com.ejercicio3.integration.departamento;

import com.ejercicio3.dto.DepartamentoCreateRequest;
import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AssignEmpleadoToDepartamentoIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldAssignEmpleadoToDepartamento() throws Exception {
        long departamentoId = createDepartamento("Compras", "Compras y abastecimiento");
        String claveEmpleado = createEmpleado("Luis", "5553000", "Password123");

        mockMvc.perform(put("/api/v1/departamentos/{departamentoId}/empleados/{claveEmpleado}", departamentoId, claveEmpleado)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.claveEmpleado").value(claveEmpleado))
                .andExpect(jsonPath("$.departamentoId").value(departamentoId))
                .andExpect(jsonPath("$.departamentoNombre").value("Compras"));

        mockMvc.perform(get("/api/v1/departamentos/{id}", departamentoId)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cantidadEmpleados").value(1));
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

    private String createEmpleado(String nombre, String telefono, String contrasena) throws Exception {
        EmpleadoCreateRequest request = new EmpleadoCreateRequest();
        request.setNombre(nombre);
        request.setTelefono(telefono);
        request.setEmail(nombre.toLowerCase().replace(" ", ".") + "@ejercicio3.local");
        request.setContrasena(contrasena);

        MvcResult result = mockMvc.perform(post("/api/v1/empleados")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();

        return objectMapper.readTree(result.getResponse().getContentAsString()).get("clave").asText();
    }
}
