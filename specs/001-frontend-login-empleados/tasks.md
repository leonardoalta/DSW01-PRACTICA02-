# Tasks: Frontend de Empleados con Login por Email

**Input**: Design documents from `/specs/001-frontend-login-empleados/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Inicialización del workspace frontend y configuración base del feature.

- [ ] T001 Inicializar workspace Angular en `frontend/` con `frontend/package.json`
- [ ] T002 Configurar scripts de desarrollo y test en `frontend/package.json`
- [ ] T003 [P] Crear configuración de entorno API en `frontend/src/environments/environment.ts`
- [ ] T004 [P] Crear configuración de entorno local en `frontend/src/environments/environment.development.ts`
- [ ] T005 Crear estructura base de app en `frontend/src/app/app.routes.ts`

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Infraestructura bloqueante para autenticación por email, modelo maestro externo y capa compartida frontend.

**⚠️ CRITICAL**: Ninguna historia se implementa hasta completar esta fase.

- [ ] T006 Agregar campo `email` (obligatorio/único) en `src/main/java/com/ejercicio3/model/Empleado.java`
- [ ] T007 [P] Ajustar consultas por email en `src/main/java/com/ejercicio3/repository/EmpleadoRepository.java`
- [ ] T008 [P] Actualizar DTOs de empleado con `email` en `src/main/java/com/ejercicio3/dto/EmpleadoCreateRequest.java`
- [ ] T009 [P] Actualizar DTOs de empleado con `email` en `src/main/java/com/ejercicio3/dto/EmpleadoUpdateRequest.java`
- [ ] T010 [P] Actualizar respuesta de empleado con `email` en `src/main/java/com/ejercicio3/dto/EmpleadoResponse.java`
- [ ] T011 Implementar entidad/configuración de master externo en `src/main/java/com/ejercicio3/model/MasterAccessConfig.java`
- [ ] T012 [P] Implementar repositorio de master externo en `src/main/java/com/ejercicio3/repository/MasterAccessConfigRepository.java`
- [ ] T013 Implementar servicio de resolución master en `src/main/java/com/ejercicio3/service/MasterAccessService.java`
- [ ] T014 [P] Agregar excepción de autorización master en `src/main/java/com/ejercicio3/exception/MasterAuthorizationException.java`
- [ ] T015 Manejar error `403` para no-master en `src/main/java/com/ejercicio3/config/GlobalExceptionHandler.java`
- [ ] T016 Crear cliente HTTP compartido en `frontend/src/app/core/http/api-client.service.ts`
- [ ] T017 [P] Crear servicio de sesión/autenticación en `frontend/src/app/core/auth/session.service.ts`
- [ ] T018 [P] Crear guard de autenticación en `frontend/src/app/core/guards/auth.guard.ts`
- [ ] T019 [P] Crear guard de permisos master en `frontend/src/app/core/guards/master.guard.ts`
- [ ] T020 Configurar interceptación Basic Auth en `frontend/src/app/core/http/auth.interceptor.ts`

**Checkpoint**: Fundación lista; US1, US2 y US3 pueden ejecutarse por prioridad.

---

## Phase 3: User Story 1 - Iniciar sesión por email (Priority: P1) 🎯 MVP

**Goal**: Permitir login por email+password y acceso a vistas protegidas con gestión de sesión.

**Independent Test**: Usuario activo entra con email válido, usuario inválido recibe rechazo claro y sesión protege rutas.

### Tests for User Story 1

- [ ] T021 [P] [US1] Agregar prueba de autenticación por email en `src/test/java/com/ejercicio3/integration/security/EmpleadoAuthenticationIntegrationTest.java`
- [ ] T022 [P] [US1] Crear prueba de componente login en `frontend/src/app/features/auth/login/login.component.spec.ts`

### Implementation for User Story 1

- [ ] T023 [US1] Adaptar carga de usuario por email en `src/main/java/com/ejercicio3/service/EmpleadoUserDetailsService.java`
- [ ] T024 [US1] Ajustar reglas de seguridad de login en `src/main/java/com/ejercicio3/config/SecurityConfig.java`
- [ ] T025 [US1] Implementar pantalla login en `frontend/src/app/features/auth/login/login.component.ts`
- [ ] T026 [P] [US1] Implementar template login en `frontend/src/app/features/auth/login/login.component.html`
- [ ] T027 [P] [US1] Implementar estilos login en `frontend/src/app/features/auth/login/login.component.scss`
- [ ] T028 [US1] Enlazar rutas protegidas y redirección post-login en `frontend/src/app/app.routes.ts`

**Checkpoint**: US1 funcional e independiente.

---

## Phase 4: User Story 2 - Gestionar empleados con escritura solo master (Priority: P2)

**Goal**: Habilitar listado para autenticados y limitar crear/editar/eliminar al usuario master externo.

**Independent Test**: Usuario no-master puede listar y no puede escribir; usuario master ejecuta CRUD completo.

### Tests for User Story 2

- [ ] T029 [P] [US2] Agregar prueba `403` para escritura no-master en `src/test/java/com/ejercicio3/integration/security/CreateEmpleadoSecurityIntegrationTest.java`
- [ ] T030 [P] [US2] Crear pruebas de servicio de empleados en `frontend/src/app/features/empleados/empleados.service.spec.ts`

### Implementation for User Story 2

- [ ] T031 [US2] Aplicar validación master en creación/edición/baja en `src/main/java/com/ejercicio3/service/EmpleadoCommandService.java`
- [ ] T032 [US2] Mantener lectura permitida para autenticados en `src/main/java/com/ejercicio3/controller/EmpleadoQueryController.java`
- [ ] T033 [US2] Implementar cliente de empleados en `frontend/src/app/features/empleados/empleados.service.ts`
- [ ] T034 [US2] Implementar listado de empleados en `frontend/src/app/features/empleados/list/empleados-list.component.ts`
- [ ] T035 [P] [US2] Implementar plantilla de listado con acciones condicionadas por master en `frontend/src/app/features/empleados/list/empleados-list.component.html`
- [ ] T036 [US2] Implementar formularios create/update/delete en `frontend/src/app/features/empleados/form/empleado-form.component.ts`
- [ ] T037 [P] [US2] Implementar plantilla formulario empleado en `frontend/src/app/features/empleados/form/empleado-form.component.html`

**Checkpoint**: US2 funcional e independiente.

---

## Phase 5: User Story 3 - Usar email obligatorio y único en ciclo de vida de empleado (Priority: P3)

**Goal**: Asegurar captura, validación y unicidad de email en backend y frontend, con contraseña opcional en edición.

**Independent Test**: Altas/ediciones con email válido pasan; email inválido o duplicado falla con feedback claro.

### Tests for User Story 3

- [ ] T038 [P] [US3] Extender validaciones de email y duplicidad en `src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoValidationIntegrationTest.java`
- [ ] T039 [P] [US3] Extender validaciones de email en edición en `src/test/java/com/ejercicio3/integration/empleado/UpdateEmpleadoValidationIntegrationTest.java`

### Implementation for User Story 3

- [ ] T040 [US3] Implementar reglas de email obligatorio/único en `src/main/java/com/ejercicio3/service/EmpleadoCommandService.java`
- [ ] T041 [US3] Permitir contraseña opcional en update en `src/main/java/com/ejercicio3/dto/EmpleadoUpdateRequest.java`
- [ ] T042 [US3] Propagar `email` en listados y detalle en `src/main/java/com/ejercicio3/service/EmpleadoQueryService.java`
- [ ] T043 [US3] Aplicar validación de email en formulario frontend en `frontend/src/app/features/empleados/form/empleado-form.component.ts`
- [ ] T044 [US3] Mostrar errores `400/409` por email en UI en `frontend/src/app/features/empleados/form/empleado-form.component.html`

**Checkpoint**: US3 funcional e independiente.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Ajustes finales, documentación y validación end-to-end.

- [ ] T045 [P] Actualizar contrato OpenAPI final en `specs/001-frontend-login-empleados/contracts/empleados-auth-v1.openapi.yaml`
- [ ] T046 [P] Actualizar contrato UI final en `specs/001-frontend-login-empleados/contracts/frontend-ui-contract.md`
- [ ] T047 Actualizar guía de ejecución y validación en `specs/001-frontend-login-empleados/quickstart.md`
- [ ] T048 Ejecutar suite backend y documentar evidencia en `target/surefire-reports/`
- [ ] T049 Ejecutar pruebas frontend y documentar evidencia en `frontend/README.md`
- [ ] T050 Implementar cierre de sesión y limpieza de sesión cliente en `frontend/src/app/core/auth/session.service.ts`
- [ ] T051 [P] Añadir trazabilidad mínima de autenticación y mantenimiento CRUD en `src/main/java/com/ejercicio3/service/EmpleadoCommandService.java`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Phase 1 (Setup)**: inicia inmediatamente.
- **Phase 2 (Foundational)**: depende de Phase 1 y bloquea todas las historias.
- **Phase 3+ (User Stories)**: dependen de Phase 2; ejecutar por prioridad P1 → P2 → P3.
- **Phase 6 (Polish)**: depende de completar las historias objetivo.

### User Story Dependencies

- **US1 (P1)**: inicia tras Foundational, sin dependencia de US2/US3.
- **US2 (P2)**: inicia tras Foundational; depende funcionalmente de sesión de US1 para flujo completo.
- **US3 (P3)**: inicia tras Foundational; se integra con flujos de edición/alta de US2.

### Within Each User Story

- Pruebas primero, luego implementación.
- Backend (modelo/servicio/seguridad) antes de integración UI.
- Componentes UI antes de validaciones y mensajes de error finos.

---

## Parallel Opportunities

- Setup: T003 y T004 pueden correr en paralelo después de T001/T002.
- Foundational: T007–T010 y T012, T014, T017–T019 pueden paralelizarse por archivos distintos.
- US1: T021 y T022 en paralelo; T026 y T027 en paralelo.
- US2: T029 y T030 en paralelo; T035 y T037 en paralelo tras servicios.
- US3: T038 y T039 en paralelo; T043 y T044 en paralelo tras T040–T042.

---

## Parallel Example: User Story 1

```bash
Task: "T021 [US1] Agregar prueba de autenticación por email en src/test/java/com/ejercicio3/integration/security/EmpleadoAuthenticationIntegrationTest.java"
Task: "T022 [US1] Crear prueba de componente login en frontend/src/app/features/auth/login/login.component.spec.ts"
Task: "T026 [US1] Implementar template login en frontend/src/app/features/auth/login/login.component.html"
Task: "T027 [US1] Implementar estilos login en frontend/src/app/features/auth/login/login.component.scss"
```

## Parallel Example: User Story 2

```bash
Task: "T029 [US2] Agregar prueba 403 para escritura no-master en src/test/java/com/ejercicio3/integration/security/CreateEmpleadoSecurityIntegrationTest.java"
Task: "T030 [US2] Crear pruebas de servicio de empleados en frontend/src/app/features/empleados/empleados.service.spec.ts"
Task: "T035 [US2] Implementar plantilla de listado con acciones condicionadas por master en frontend/src/app/features/empleados/list/empleados-list.component.html"
Task: "T037 [US2] Implementar plantilla formulario empleado en frontend/src/app/features/empleados/form/empleado-form.component.html"
```

## Parallel Example: User Story 3

```bash
Task: "T038 [US3] Extender validaciones de email y duplicidad en src/test/java/com/ejercicio3/integration/empleado/CreateEmpleadoValidationIntegrationTest.java"
Task: "T039 [US3] Extender validaciones de email en edición en src/test/java/com/ejercicio3/integration/empleado/UpdateEmpleadoValidationIntegrationTest.java"
Task: "T043 [US3] Aplicar validación de email en formulario frontend en frontend/src/app/features/empleados/form/empleado-form.component.ts"
Task: "T044 [US3] Mostrar errores 400/409 por email en UI en frontend/src/app/features/empleados/form/empleado-form.component.html"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Completar Phase 1 y Phase 2.
2. Implementar y validar US1 (login por email + sesión).
3. Demostrar acceso protegido con usuario activo.

### Incremental Delivery

1. US1: autenticación y acceso seguro.
2. US2: CRUD con permisos master en escritura.
3. US3: robustez de email obligatorio/único y UX de validaciones.
4. Polish: contratos, quickstart y evidencia final.
