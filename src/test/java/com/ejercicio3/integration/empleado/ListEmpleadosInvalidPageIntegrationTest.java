package com.ejercicio3.integration.empleado;

import com.ejercicio3.integration.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ListEmpleadosInvalidPageIntegrationTest extends BaseIntegrationTest {

    @Test
    void shouldReturnBadRequestForInvalidPage() throws Exception {
        mockMvc.perform(get("/api/v1/empleados?page=-1").with(httpBasic(AUTH_USER, AUTH_PASSWORD)))
                .andExpect(status().isBadRequest());
    }
}
