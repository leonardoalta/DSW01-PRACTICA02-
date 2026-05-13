# Tasks: Ajuste CRUD Empleados a Constitución v2.1 (PK autogenerada)

**Input**: Design documents from `/specs/002-constitucion-v2-auth-api/`
**Prerequisites**: plan.md (required), spec.md (required), research.md, data-model.md, contracts/empleados-v1.openapi.yaml

**Tests**: Se incluyen tareas de pruebas porque el spec exige validación independiente por historia y el plan exige cobertura automatizada.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Ajustar baseline de configuración y contrato para soportar `clave` autogenerada.

- [ ] T001 Actualizar descripción funcional del módulo para reflejar PK autogenerada en ./pom.xml
- [ ] T002 Definir/validar propiedad de base path versionada en src/main/resources/application.properties
- [ ] T003 [P] Alinear perfil de pruebas para inicialización de datos y seguridad en src/test/resources/application-test.properties
- [ ] T004 [P] Actualizar semilla de autenticación de desarrollo en src/main/resources/data.sql

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura y modelo base que bloquean cualquier historia.

**⚠️ CRITICAL**: No iniciar historias de usuario hasta completar esta fase.

- [ ] T005 Definir estrategia de generación automática de `clave` en src/main/java/com/ejercicio3/model/Empleado.java
- [X] T006 Implementar generador de clave canónica en src/main/java/com/ejercicio3/service/EmpleadoClaveGenerator.java
- [ ] T007 [P] Ajustar repositorio para búsquedas y ordenación por `clave` generada en src/main/java/com/ejercicio3/repository/EmpleadoRepository.java
- [X] T008 Adaptar DTO de creación para remover `clave` de entrada en src/main/java/com/ejercicio3/dto/EmpleadoCreateRequest.java
- [ ] T009 [P] Verificar que `EmpleadoResponse` expone `clave` generada sin contrasenaHash en src/main/java/com/ejercicio3/dto/EmpleadoResponse.java
- [X] T010 Configurar servicio de comando para asignar `clave` autogenerada en src/main/java/com/ejercicio3/service/EmpleadoCommandService.java
- [ ] T011 [P] Mantener autenticación por `clave` en src/main/java/com/ejercicio3/service/EmpleadoUserDetailsService.java
- [ ] T012 [P] Alinear contrato runtime OpenAPI en src/main/java/com/ejercicio3/config/OpenApiConfig.java
- [ ] T013 Actualizar fixture base de integración para nuevo modelo en src/test/java/com/ejercicio3/integration/BaseIntegrationTest.java
- [ ] T014 [P] Sincronizar contrato OpenAPI v1 con create sin `clave` en specs/002-constitucion-v2-auth-api/contracts/empleados-v1.openapi.yaml

**Checkpoint**: Modelo y contrato base listos para implementar historias.

---

## Phase 3: User Story 1 - Registrar empleados sin enviar clave (Priority: P1) 🎯 MVP

**Goal**: Registrar empleados con `clave` autogenerada, validaciones de payload y contraseña segura.

**Independent Test**: Ejecutar `POST /api/v1/empleados` enviando `nombre`, `telefono`, `contrasena` y verificar `201` con `clave` generada en respuesta.

### Tests for User Story 1

- [X] T015 [P] [US1] Agregar prueba de create exitoso sin `clave` de entrada en src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoIntegrationTest.java
- [X] T016 [P] [US1] Agregar prueba de rechazo/ignorado de `clave` enviada manualmente en src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoValidationIntegrationTest.java
- [X] T017 [P] [US1] Mantener prueba de validación de contraseña mínima en src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoValidationIntegrationTest.java
- [X] T018 [P] [US1] Verificar autenticación con empleado creado dinámicamente en src/test/java/com/ejercicio3/integration/security/EmpleadoAuthenticationIntegrationTest.java

### Implementation for User Story 1

- [X] T019 [US1] Implementar generación de `clave` dentro de create en src/main/java/com/ejercicio3/service/EmpleadoCommandService.java
- [X] T020 [US1] Asegurar hash de contraseña en alta en src/main/java/com/ejercicio3/service/EmpleadoCommandService.java
- [X] T021 [US1] Ajustar endpoint POST a payload final sin `clave` en src/main/java/com/ejercicio3/controller/EmpleadoCommandController.java
- [ ] T022 [US1] Alinear manejo de errores de validación en src/main/java/com/ejercicio3/config/GlobalExceptionHandler.java

**Checkpoint**: Alta funcional con `clave` autogenerada y seguridad operativa.

---

## Phase 4: User Story 2 - Consultar empleados con clave generada (Priority: P2)

**Goal**: Consultar listado y detalle usando `clave` generada automáticamente.

**Independent Test**: Crear empleado y consultar por `GET /api/v1/empleados` y `GET /api/v1/empleados/{clave}` usando la `clave` retornada por create.

### Tests for User Story 2

- [X] T023 [P] [US2] Ajustar prueba de listado sin paginación para claves autogeneradas en src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosNoPaginationIntegrationTest.java
- [X] T024 [P] [US2] Ajustar prueba de listado paginado para claves autogeneradas en src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosPaginationIntegrationTest.java
- [X] T025 [P] [US2] Mantener prueba de página inválida en src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosInvalidPageIntegrationTest.java
- [X] T026 [P] [US2] Ajustar prueba de consulta por clave usando valor generado en src/test/java/com/ejercicio3/integration/empleado/GetEmpleadoByClaveIntegrationTest.java

### Implementation for User Story 2

- [ ] T027 [US2] Validar que listado retorna empleados activos con `clave` generada en src/main/java/com/ejercicio3/service/EmpleadoQueryService.java
- [ ] T028 [US2] Validar consulta por clave en endpoint versionado en src/main/java/com/ejercicio3/controller/EmpleadoQueryController.java
- [ ] T029 [US2] Confirmar shape de respuesta paginada en src/main/java/com/ejercicio3/dto/EmpleadoPageResponse.java

**Checkpoint**: Consultas por detalle/listado funcionan con claves autogeneradas.

---

## Phase 5: User Story 3 - Actualizar y eliminar por clave generada (Priority: P3)

**Goal**: Mantener actualización y eliminación lógica usando la `clave` autogenerada como identificador.

**Independent Test**: Crear empleado, actualizar con `PUT /api/v1/empleados/{clave}`, eliminar con `DELETE /api/v1/empleados/{clave}` y validar 404/conflict esperados.

### Tests for User Story 3

- [X] T030 [P] [US3] Ajustar prueba de actualización exitosa con clave generada en src/test/java/com/ejercicio3/integration/empleado/UpdateEmpleadoIntegrationTest.java
- [X] T031 [P] [US3] Mantener prueba de validaciones de actualización en src/test/java/com/ejercicio3/integration/empleado/UpdateEmpleadoValidationIntegrationTest.java
- [X] T032 [P] [US3] Ajustar prueba de delete lógico con clave generada en src/test/java/com/ejercicio3/integration/empleado/DeleteEmpleadoIntegrationTest.java
- [ ] T033 [P] [US3] Ajustar prueba de update/delete not found con claves generadas en src/test/java/com/ejercicio3/integration/empleado/UpdateDeleteNotFoundIntegrationTest.java

### Implementation for User Story 3

- [ ] T034 [US3] Verificar update por clave generada y re-hash opcional en src/main/java/com/ejercicio3/service/EmpleadoCommandService.java
- [ ] T035 [US3] Verificar delete lógico y conflicto por inactivo en src/main/java/com/ejercicio3/service/EmpleadoCommandService.java
- [ ] T036 [US3] Validar endpoints PUT/DELETE versionados por clave en src/main/java/com/ejercicio3/controller/EmpleadoCommandController.java

**Checkpoint**: Mantenimiento completo por `clave` autogenerada funcional.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre de consistencia documental y verificación integral.

- [ ] T037 [P] Actualizar quickstart para create sin `clave` en specs/002-constitucion-v2-auth-api/quickstart.md
- [ ] T038 [P] Verificar consistencia final del spec en specs/002-constitucion-v2-auth-api/spec.md
- [ ] T039 Ejecutar suite completa y registrar evidencia en specs/002-constitucion-v2-auth-api/quickstart.md
- [ ] T040 Validar que Swagger runtime y contrato coinciden para create/update/query en src/main/java/com/ejercicio3/config/OpenApiConfig.java

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: inicia inmediatamente.
- **Phase 2 (Foundational)**: depende de Phase 1 y bloquea todas las historias.
- **Phase 3 (US1)**: depende de Phase 2; define el MVP.
- **Phase 4 (US2)**: depende de Phase 2 y del alta funcional de US1 para usar claves generadas reales.
- **Phase 5 (US3)**: depende de Phase 2 y de create funcional de US1 para actualización/eliminación.
- **Phase 6 (Polish)**: depende de historias objetivo completas.

### User Story Dependencies

- **US1 (P1)**: sin dependencias de otras historias.
- **US2 (P2)**: depende de US1 para obtener `clave` generada en pruebas de consulta.
- **US3 (P3)**: depende de US1 para obtener `clave` generada en pruebas de mantenimiento.

### Within Each User Story

- Escribir/ajustar pruebas primero y validarlas en rojo.
- Implementar modelo/servicio.
- Implementar/controlar endpoints.
- Cerrar con validación de contrato y pruebas en verde.

### Dependency Graph (Story Order)

- US1 → US2
- US1 → US3
- US2 y US3 pueden ejecutarse en paralelo después de US1.

### Parallel Opportunities

- Setup: T003 y T004.
- Foundational: T007, T009, T011, T012 y T014.
- US1 tests: T015–T018.
- US2 tests: T023–T026.
- US3 tests: T030–T033.
- Polish: T037 y T038.

---

## Parallel Example: User Story 1

```bash
# Pruebas US1 en paralelo
T015
T016
T017
T018

# Implementación parcial paralela US1
T020
T022
```

## Parallel Example: User Story 2

```bash
# Pruebas de consulta en paralelo
T023
T024
T025
T026
```

## Parallel Example: User Story 3

```bash
# Pruebas de mantenimiento en paralelo
T030
T031
T032
T033
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 y Phase 2.
2. Completar Phase 3 (US1).
3. Validar create sin `clave` y respuesta con `clave` generada.
4. Demo interna del MVP.

### Incremental Delivery

1. Entrega 1: US1 (alta con `clave` autogenerada + seguridad).
2. Entrega 2: US2 (consultas con `clave` generada + paginación).
3. Entrega 3: US3 (actualizar/eliminar por `clave` generada).
4. Entrega 4: Polish (documentación + suite completa).
