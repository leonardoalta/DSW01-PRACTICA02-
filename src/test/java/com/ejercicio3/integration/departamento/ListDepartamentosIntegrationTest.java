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

class ListDepartamentosIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldListDepartamentosWithCantidadEmpleados() throws Exception {
        long dep1 = createDepartamento("Soporte", "Atencion");
        long dep2 = createDepartamento("RRHH", "Capital humano");
        String claveEmpleado = createEmpleado("Ana", "5551000", "Password123");

        mockMvc.perform(put("/api/v1/departamentos/{departamentoId}/empleados/{claveEmpleado}", dep1, claveEmpleado)
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/departamentos")
                        .with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(dep1))
                .andExpect(jsonPath("$[0].cantidadEmpleados").value(1))
                .andExpect(jsonPath("$[1].id").value(dep2))
                .andExpect(jsonPath("$[1].cantidadEmpleados").value(0));
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
