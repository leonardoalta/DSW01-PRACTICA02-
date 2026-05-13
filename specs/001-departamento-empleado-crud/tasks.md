# Tasks: CRUD de Departamentos relacionados con Empleados

**Input**: Design documents from `/specs/001-departamento-empleado-crud/`
**Prerequisites**: plan.md (required), spec.md (required), research.md, data-model.md, contracts/departamentos-v1.openapi.yaml

**Tests**: Se incluyen tareas de pruebas porque el spec exige validación independiente por historia y criterios de éxito medibles.

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Ajustar baseline de configuración y documentación para introducir módulo de departamentos.

- [X] T001 Actualizar descripción funcional del módulo para incluir departamentos en `pom.xml`
- [X] T002 [P] Validar configuración base versionada `/api/v1` en `src/main/resources/application.properties`
- [X] T003 [P] Revisar configuración de pruebas para H2 y seguridad en `src/test/resources/application-test.properties`
- [X] T004 [P] Actualizar guía rápida del feature en `specs/001-departamento-empleado-crud/quickstart.md`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Definir modelo persistente y piezas núcleo que bloquean las historias.

**⚠️ CRITICAL**: No iniciar historias de usuario hasta completar esta fase.

- [X] T005 Crear entidad `Departamento` con auditoría en `src/main/java/com/ejercicio3/model/Departamento.java`
- [X] T006 Actualizar entidad `Empleado` para referencia opcional `departamento` en `src/main/java/com/ejercicio3/model/Empleado.java`
- [X] T007 [P] Crear repositorio de departamentos en `src/main/java/com/ejercicio3/repository/DepartamentoRepository.java`
- [X] T008 [P] Extender `EmpleadoRepository` con consultas por departamento en `src/main/java/com/ejercicio3/repository/EmpleadoRepository.java`
- [X] T009 Crear DTOs base de departamento (`create/update/response`) en `src/main/java/com/ejercicio3/dto/DepartamentoCreateRequest.java`
- [X] T010 [P] Crear DTO de detalle y resumen de empleados por departamento en `src/main/java/com/ejercicio3/dto/DepartamentoDetalleResponse.java`
- [X] T011 [P] Crear DTO de asignación empleado-departamento en `src/main/java/com/ejercicio3/dto/AsignacionDepartamentoResponse.java`
- [X] T012 Implementar mapper de departamento y conteo de asociados en `src/main/java/com/ejercicio3/dto/DepartamentoMapper.java`
- [X] T013 [P] Agregar excepciones de conflicto de departamento en `src/main/java/com/ejercicio3/exception/DepartamentoConflictException.java`
- [X] T014 Actualizar `GlobalExceptionHandler` para conflictos y validaciones de departamento en `src/main/java/com/ejercicio3/config/GlobalExceptionHandler.java`
- [X] T015 [P] Sincronizar contrato OpenAPI de departamentos en `specs/001-departamento-empleado-crud/contracts/departamentos-v1.openapi.yaml`

**Checkpoint**: Base de dominio y contrato listos para historias.

---

## Phase 3: User Story 1 - Registrar y mantener departamentos (Priority: P1) 🎯 MVP

**Goal**: Crear, actualizar y eliminar departamentos con reglas de validación y conflicto.

**Independent Test**: Crear departamento, actualizarlo y eliminarlo cuando no tenga empleados asociados.

### Tests for User Story 1

- [X] T016 [P] [US1] Crear prueba de alta exitosa de departamento en `src/test/java/com/ejercicio3/integration/departamento/CreateDepartamentoIntegrationTest.java`
- [X] T017 [P] [US1] Crear prueba de validación de nombre requerido/longitud en `src/test/java/com/ejercicio3/integration/departamento/CreateDepartamentoValidationIntegrationTest.java`
- [X] T018 [P] [US1] Crear prueba de nombre duplicado en `src/test/java/com/ejercicio3/integration/departamento/CreateDepartamentoDuplicateIntegrationTest.java`
- [X] T019 [P] [US1] Crear prueba de actualización exitosa de departamento en `src/test/java/com/ejercicio3/integration/departamento/UpdateDepartamentoIntegrationTest.java`
- [X] T020 [P] [US1] Crear prueba de eliminación exitosa sin empleados asociados en `src/test/java/com/ejercicio3/integration/departamento/DeleteDepartamentoIntegrationTest.java`

### Implementation for User Story 1

- [X] T021 [US1] Implementar `DepartamentoCommandService#create` en `src/main/java/com/ejercicio3/service/DepartamentoCommandService.java`
- [X] T022 [US1] Implementar `DepartamentoCommandService#update` en `src/main/java/com/ejercicio3/service/DepartamentoCommandService.java`
- [X] T023 [US1] Implementar `DepartamentoCommandService#delete` con regla sin empleados asociados en `src/main/java/com/ejercicio3/service/DepartamentoCommandService.java`
- [X] T024 [US1] Exponer endpoints POST/PUT/DELETE de departamentos en `src/main/java/com/ejercicio3/controller/DepartamentoCommandController.java`
- [X] T025 [US1] Registrar mensajes de auditoría para create/update/delete en `src/main/java/com/ejercicio3/service/DepartamentoCommandService.java`

**Checkpoint**: CRUD de mantenimiento de departamento funcional y validado.

---

## Phase 4: User Story 2 - Consultar departamentos y su relación con empleados (Priority: P2)

**Goal**: Consultar listado y detalle de departamentos con cantidad/lista de empleados asociados.

**Independent Test**: Listar departamentos y consultar detalle verificando datos y cantidad de empleados asociados.

### Tests for User Story 2

- [X] T026 [P] [US2] Crear prueba de listado de departamentos con `cantidadEmpleados` en `src/test/java/com/ejercicio3/integration/departamento/ListDepartamentosIntegrationTest.java`
- [X] T027 [P] [US2] Crear prueba de detalle con empleados asociados en `src/test/java/com/ejercicio3/integration/departamento/GetDepartamentoByIdIntegrationTest.java`
- [X] T028 [P] [US2] Crear prueba de detalle no encontrado en `src/test/java/com/ejercicio3/integration/departamento/GetDepartamentoNotFoundIntegrationTest.java`

### Implementation for User Story 2

- [X] T029 [US2] Implementar `DepartamentoQueryService#list` con conteo de empleados en `src/main/java/com/ejercicio3/service/DepartamentoQueryService.java`
- [X] T030 [US2] Implementar `DepartamentoQueryService#getById` con listado de empleados en `src/main/java/com/ejercicio3/service/DepartamentoQueryService.java`
- [X] T031 [US2] Exponer endpoints GET de departamentos en `src/main/java/com/ejercicio3/controller/DepartamentoQueryController.java`

**Checkpoint**: Consultas de departamento completas y consistentes.

---

## Phase 5: User Story 3 - Asociar empleados a departamentos (Priority: P3)

**Goal**: Asignar y reasignar empleados a departamentos existentes.

**Independent Test**: Asignar empleado a un departamento, reasignarlo a otro y verificar reflejo en consultas.

### Tests for User Story 3

- [X] T032 [P] [US3] Crear prueba de asignación de empleado a departamento en `src/test/java/com/ejercicio3/integration/departamento/AssignEmpleadoToDepartamentoIntegrationTest.java`
- [X] T033 [P] [US3] Crear prueba de reasignación de empleado entre departamentos en `src/test/java/com/ejercicio3/integration/departamento/ReassignEmpleadoDepartamentoIntegrationTest.java`
- [X] T034 [P] [US3] Crear prueba de asignación con empleado inexistente/departamento inexistente en `src/test/java/com/ejercicio3/integration/departamento/AssignEmpleadoNotFoundIntegrationTest.java`
- [X] T035 [P] [US3] Crear prueba de conflicto al eliminar departamento con empleados asociados en `src/test/java/com/ejercicio3/integration/departamento/DeleteDepartamentoConflictIntegrationTest.java`

### Implementation for User Story 3

- [X] T036 [US3] Implementar `DepartamentoCommandService#assignEmpleado` en `src/main/java/com/ejercicio3/service/DepartamentoCommandService.java`
- [X] T037 [US3] Exponer endpoint de asignación/reasignación en `src/main/java/com/ejercicio3/controller/DepartamentoCommandController.java`
- [X] T038 [US3] Ajustar consulta de detalle/listado para reflejar reasignaciones en `src/main/java/com/ejercicio3/service/DepartamentoQueryService.java`

**Checkpoint**: Relación departamento-empleado operativa con asignación/reasignación.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Cierre de consistencia integral, documentación y validación final.

- [X] T039 [P] Actualizar documentación OpenAPI runtime para incluir departamentos en `src/main/java/com/ejercicio3/config/OpenApiConfig.java`
- [X] T040 [P] Añadir datos de ejemplo de departamentos en `src/main/resources/data.sql`
- [X] T041 Validar quickstart ejecutando flujo CRUD + relación en `specs/001-departamento-empleado-crud/quickstart.md`
- [X] T042 Ejecutar suite completa y registrar evidencia en `specs/001-departamento-empleado-crud/quickstart.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: inicia inmediatamente.
- **Phase 2 (Foundational)**: depende de Setup y bloquea historias.
- **Phase 3 (US1)**: depende de Foundational y define MVP.
- **Phase 4 (US2)**: depende de Foundational y de base CRUD de US1.
- **Phase 5 (US3)**: depende de Foundational y de existencia de departamentos/consultas.
- **Phase 6 (Polish)**: depende de historias completadas.

### User Story Dependencies

- **US1 (P1)**: sin dependencias de otras historias.
- **US2 (P2)**: depende de US1 para disponer de departamentos reales.
- **US3 (P3)**: depende de US1 y US2 para asignar/reasignar y verificar consultas.

### Within Each User Story

- Escribir pruebas primero y validarlas en rojo.
- Implementar servicios y repositorios requeridos.
- Exponer endpoints y mapear errores.
- Cerrar con validación de contrato y pruebas en verde.

### Dependency Graph (Story Order)

- US1 → US2
- US1 → US3
- US2 → US3

### Parallel Opportunities

- Setup: T002, T003, T004.
- Foundational: T007, T008, T010, T011, T013, T015.
- US1 tests: T016–T020.
- US2 tests: T026–T028.
- US3 tests: T032–T035.
- Polish: T039, T040.

---

## Parallel Example: User Story 1

```bash
# Pruebas US1 en paralelo
T016
T017
T018
T019
T020

# Implementación parcial paralela US1
T021
T024
```

## Parallel Example: User Story 2

```bash
# Pruebas de consulta en paralelo
T026
T027
T028
```

## Parallel Example: User Story 3

```bash
# Pruebas de relación en paralelo
T032
T033
T034
T035
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 y Phase 2.
2. Completar Phase 3 (US1).
3. Validar creación, actualización y eliminación con reglas de conflicto.
4. Presentar demo del CRUD base de departamentos.

### Incremental Delivery

1. Entrega 1: US1 (CRUD base de departamentos).
2. Entrega 2: US2 (consultas y detalle con empleados asociados).
3. Entrega 3: US3 (asignación/reasignación de empleados).
4. Entrega 4: Polish (documentación, quickstart y suite completa).
