package com.ejercicio3.integration.empleado;

import com.ejercicio3.dto.EmpleadoCreateRequest;
import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ListEmpleadosNoPaginationIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldListAllActiveEmpleadosWithoutPagination() throws Exception {
        for (int i = 1; i <= 2; i++) {
            EmpleadoCreateRequest request = new EmpleadoCreateRequest();
            request.setNombre("Empleado " + i);
            request.setTelefono("555000" + i);
            request.setEmail("empleado.nopage." + i + "@ejercicio3.local");
            request.setContrasena("clave1234");

            mockMvc.perform(post("/api/v1/empleados")
                            .with(httpBasic(AUTH_USER, AUTH_PASSWORD))
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isCreated());
        }

        mockMvc.perform(get("/api/v1/empleados").with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(3));
    }
}
