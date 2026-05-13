# Tasks: CRUD de Empleados

**Input**: Design documents from `/specs/003-crud-empleados/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Se incluyen tareas de pruebas automáticas porque el plan y la constitución exigen validación automatizada de seguridad, persistencia y contratos.

**Organization**: Tasks are grouped by user story to enable independent implementation and testing of each story.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Inicializar proyecto backend Spring Boot 3 + Java 17 y base de configuración.

- [X] T001 Crear estructura base del proyecto Maven en `pom.xml`
- [X] T002 Configurar dependencias Spring Boot, JPA, Security, Validation, PostgreSQL y springdoc en `pom.xml`
- [X] T003 [P] Crear clase de arranque en `src/main/java/com/ejercicio3/Ejercicio3Application.java`
- [X] T004 [P] Crear configuración inicial en `src/main/resources/application.properties`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura compartida obligatoria antes de cualquier historia.

**⚠️ CRITICAL**: No user story work can begin until this phase is complete

- [X] T005 Crear archivo de provisión PostgreSQL en Docker en `docker/docker-compose.yml`
- [X] T006 Configurar seguridad HTTP Basic con credenciales de desarrollo en `src/main/java/com/ejercicio3/config/SecurityConfig.java`
- [X] T007 [P] Definir entidad base `Empleado` con eliminación lógica en `src/main/java/com/ejercicio3/model/Empleado.java`
- [X] T008 [P] Definir repositorio base de empleados en `src/main/java/com/ejercicio3/repository/EmpleadoRepository.java`
- [X] T009 [P] Crear manejo global de errores y validaciones en `src/main/java/com/ejercicio3/config/GlobalExceptionHandler.java`
- [X] T010 [P] Habilitar documentación Swagger/OpenAPI en `src/main/java/com/ejercicio3/config/OpenApiConfig.java`
- [X] T011 Crear DTOs base compartidos en `src/main/java/com/ejercicio3/dto/`
- [X] T012 Configurar utilidades de auditoría (`createdAt`, `updatedAt`) en `src/main/java/com/ejercicio3/config/AuditingConfig.java`

**Checkpoint**: Foundation ready - user story implementation can now begin

---

## Phase 3: User Story 1 - Registrar empleados (Priority: P1) 🎯 MVP

**Goal**: Permitir alta de empleados con clave única, nombre y teléfono válidos.

**Independent Test**: Crear un empleado válido autenticado, verificar persistencia, y comprobar rechazo por clave duplicada y longitud >100.

### Tests for User Story 1

- [X] T013 [P] [US1] Crear prueba de integración de creación exitosa en `src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoIntegrationTest.java`
- [X] T014 [P] [US1] Crear prueba de integración para clave duplicada en `src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoDuplicateIntegrationTest.java`
- [X] T015 [P] [US1] Crear prueba de integración para validación de longitud en `src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoValidationIntegrationTest.java`
- [X] T016 [P] [US1] Crear prueba de seguridad para alta sin credenciales en `src/test/java/com/ejercicio3/integration/security/CreateEmpleadoSecurityIntegrationTest.java`

### Implementation for User Story 1

- [X] T017 [US1] Implementar DTO de request de creación en `src/main/java/com/ejercicio3/dto/EmpleadoCreateRequest.java`
- [X] T018 [US1] Implementar DTO de respuesta de empleado en `src/main/java/com/ejercicio3/dto/EmpleadoResponse.java`
- [X] T019 [US1] Implementar lógica de alta y validación de duplicados en `src/main/java/com/ejercicio3/service/EmpleadoCommandService.java`
- [X] T020 [US1] Implementar endpoint POST `/api/empleados` en `src/main/java/com/ejercicio3/controller/EmpleadoCommandController.java`
- [X] T021 [US1] Actualizar contrato para alta y errores de creación en `specs/003-crud-empleados/contracts/empleados.openapi.yaml`

**Checkpoint**: User Story 1 funcional y verificable de forma independiente

---

## Phase 4: User Story 2 - Consultar empleados (Priority: P2)

**Goal**: Consultar empleados activos por clave y listar todos o paginados (5 por página si se solicita).

**Independent Test**: Consultar por clave existente/inexistente, listar sin `page` (todo) y listar con `page` (size=5 + metadatos).

### Tests for User Story 2

- [X] T022 [P] [US2] Crear prueba de integración para consulta por clave existente/inexistente en `src/test/java/com/ejercicio3/integration/empleado/GetEmpleadoByClaveIntegrationTest.java`
- [X] T023 [P] [US2] Crear prueba de integración para listado sin paginación en `src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosNoPaginationIntegrationTest.java`
- [X] T024 [P] [US2] Crear prueba de integración para listado paginado size=5 en `src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosPaginationIntegrationTest.java`
- [X] T025 [P] [US2] Crear prueba de integración para parámetro `page` inválido en `src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosInvalidPageIntegrationTest.java`

### Implementation for User Story 2

- [X] T026 [US2] Implementar DTO de respuesta paginada en `src/main/java/com/ejercicio3/dto/EmpleadoPageResponse.java`
- [X] T027 [US2] Implementar consultas de empleados activos y paginadas en `src/main/java/com/ejercicio3/service/EmpleadoQueryService.java`
- [X] T028 [US2] Implementar endpoint GET `/api/empleados` y GET `/api/empleados/{clave}` en `src/main/java/com/ejercicio3/controller/EmpleadoQueryController.java`
- [X] T029 [US2] Actualizar repositorio con consultas por `activo` y paginación en `src/main/java/com/ejercicio3/repository/EmpleadoRepository.java`
- [X] T030 [US2] Actualizar contrato para listado paginado/no paginado en `specs/003-crud-empleados/contracts/empleados.openapi.yaml`

**Checkpoint**: User Stories 1 y 2 funcionales e independientes

---

## Phase 5: User Story 3 - Actualizar y eliminar empleados (Priority: P3)

**Goal**: Actualizar datos de empleados activos y realizar eliminación lógica con control de estado.

**Independent Test**: Actualizar empleado activo, eliminar lógicamente, validar no encontrado para clave inexistente y conflicto al eliminar ya inactivo.

### Tests for User Story 3

- [X] T031 [P] [US3] Crear prueba de integración para actualización exitosa en `src/test/java/com/ejercicio3/integration/empleado/UpdateEmpleadoIntegrationTest.java`
- [X] T032 [P] [US3] Crear prueba de integración para eliminación lógica exitosa en `src/test/java/com/ejercicio3/integration/empleado/DeleteEmpleadoIntegrationTest.java`
- [X] T033 [P] [US3] Crear prueba de integración para conflicto de estado al eliminar inactivo en `src/test/java/com/ejercicio3/integration/empleado/DeleteEmpleadoConflictIntegrationTest.java`
- [X] T034 [P] [US3] Crear prueba de integración para update/delete de clave inexistente en `src/test/java/com/ejercicio3/integration/empleado/UpdateDeleteNotFoundIntegrationTest.java`

### Implementation for User Story 3

- [X] T035 [US3] Implementar DTO de actualización en `src/main/java/com/ejercicio3/dto/EmpleadoUpdateRequest.java`
- [X] T036 [US3] Implementar lógica de actualización y eliminación lógica en `src/main/java/com/ejercicio3/service/EmpleadoCommandService.java`
- [X] T037 [US3] Implementar endpoints PUT/DELETE en `src/main/java/com/ejercicio3/controller/EmpleadoCommandController.java`
- [X] T038 [US3] Actualizar manejo de errores de conflicto de estado en `src/main/java/com/ejercicio3/config/GlobalExceptionHandler.java`
- [X] T039 [US3] Actualizar contrato para update/delete y conflicto 409 en `specs/003-crud-empleados/contracts/empleados.openapi.yaml`

**Checkpoint**: Todas las historias funcionales de forma independiente

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Endurecimiento y validación transversal.

- [X] T040 [P] Actualizar guía de ejecución y verificación manual en `specs/003-crud-empleados/quickstart.md`
- [X] T041 Ejecutar suite completa y ajustar documentación de resultados en `specs/003-crud-empleados/quickstart.md`
- [X] T042 [P] Verificar consistencia final de contrato OpenAPI en `specs/003-crud-empleados/contracts/empleados.openapi.yaml`
- [X] T043 [P] Añadir logging estructurado de operaciones CRUD en `src/main/java/com/ejercicio3/service/EmpleadoCommandService.java`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion - BLOCKS all user stories
- **User Stories (Phase 3+)**: Depend on Foundational completion
- **Polish (Phase 6)**: Depends on completion of selected user stories

### User Story Dependencies

- **US1 (P1)**: inicia tras Phase 2; no depende de US2/US3
- **US2 (P2)**: inicia tras Phase 2; reutiliza entidad/repositorio de Foundation y puede ejecutarse tras US1 si comparte controladores
- **US3 (P3)**: inicia tras Phase 2; reutiliza servicios de comandos y depende funcionalmente de existencia de empleados

### Within Each User Story

- Tests first (crear pruebas antes de implementación de la historia)
- DTO/model adjustments before services
- Services before controllers/endpoints
- Contract update before story checkpoint

### Dependency Graph (Story Completion Order)

- Foundation → US1 → US2 → US3 → Polish
- Foundation → US2 (parallelizable with US1 if se separan archivos de controladores/servicios)
- Foundation → US3 (puede avanzar en paralelo en pruebas y lógica de comando)

---

## Parallel Opportunities

- **Phase 1**: T003 y T004 pueden ejecutarse en paralelo tras T001/T002.
- **Phase 2**: T007, T008, T009 y T010 pueden ejecutarse en paralelo.
- **US1**: T013-T016 en paralelo; T017 y T018 en paralelo.
- **US2**: T022-T025 en paralelo; T026 y T029 en paralelo.
- **US3**: T031-T034 en paralelo; T035 y T038 en paralelo.
- **Polish**: T040, T042 y T043 en paralelo.

### Parallel Example: User Story 1

```bash
Task T013: src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoIntegrationTest.java
Task T014: src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoDuplicateIntegrationTest.java
Task T015: src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoValidationIntegrationTest.java
Task T016: src/test/java/com/ejercicio3/integration/security/CreateEmpleadoSecurityIntegrationTest.java
```

### Parallel Example: User Story 2

```bash
Task T022: src/test/java/com/ejercicio3/integration/empleado/GetEmpleadoByClaveIntegrationTest.java
Task T023: src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosNoPaginationIntegrationTest.java
Task T024: src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosPaginationIntegrationTest.java
Task T025: src/test/java/com/ejercicio3/integration/empleado/ListEmpleadosInvalidPageIntegrationTest.java
```

### Parallel Example: User Story 3

```bash
Task T031: src/test/java/com/ejercicio3/integration/empleado/UpdateEmpleadoIntegrationTest.java
Task T032: src/test/java/com/ejercicio3/integration/empleado/DeleteEmpleadoIntegrationTest.java
Task T033: src/test/java/com/ejercicio3/integration/empleado/DeleteEmpleadoConflictIntegrationTest.java
Task T034: src/test/java/com/ejercicio3/integration/empleado/UpdateDeleteNotFoundIntegrationTest.java
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 + Phase 2
2. Completar US1 (Phase 3)
3. Validar creación, duplicados, validaciones y autenticación
4. Demostrar MVP con alta funcional

### Incremental Delivery

1. MVP: US1 (alta)
2. Incremento 2: US2 (consulta + paginación opcional)
3. Incremento 3: US3 (actualización + eliminación lógica)
4. Cierre: Polish y validación end-to-end

### Parallel Team Strategy

1. Equipo A: Foundation + seguridad
2. Equipo B: US1 comandos
3. Equipo C: US2 consultas/paginación
4. Equipo D: US3 update/delete

---

## Notes

- Todas las tareas siguen formato checklist obligatorio (`- [X] Txxx ...`).
- Historias US1/US2/US3 quedan trazables por etiqueta.
- Cada historia contiene criterio de prueba independiente.
- Contrato OpenAPI y quickstart se actualizan durante implementación, no al final únicamente.
