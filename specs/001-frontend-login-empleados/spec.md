# Feature Specification: Frontend de Empleados con Login por Email

**Feature Branch**: `001-frontend-login-empleados`  
**Created**: 2026-03-11  
**Status**: Draft  
**Input**: User description: "Crea el Fronted que permita un CRUD de empleados y un login que tome el email y el password; antes agregar campo email al empleado para usarlo en el logeo"

## Clarifications

### Session 2026-03-11

- Q: ¿Qué identificador se usará para iniciar sesión en el frontend? → A: Email + password del empleado.
- Q: ¿Quién puede ejecutar operaciones CRUD de empleados? → A: Todos los usuarios autenticados pueden ingresar al módulo; solo el usuario master puede crear, editar y eliminar.
- Q: ¿Cómo se define el usuario master? → A: Se define fuera de `Empleado`, en configuración/tabla separada.

## User Scenarios & Testing *(mandatory)*

<!--
  IMPORTANT: User stories should be PRIORITIZED as user journeys ordered by importance.
  Each user story/journey must be INDEPENDENTLY TESTABLE - meaning if you implement just ONE of them,
  you should still have a viable MVP (Minimum Viable Product) that delivers value.
  
  Assign priorities (P1, P2, P3, etc.) to each story, where P1 is the most critical.
  Think of each story as a standalone slice of functionality that can be:
  - Developed independently
  - Tested independently
  - Deployed independently
  - Demonstrated to users independently
-->

### User Story 1 - Iniciar sesión por email (Priority: P1)

Como empleado, quiero iniciar sesión con mi email y contraseña para acceder al módulo de gestión.

**Why this priority**: Sin autenticación no es posible usar de forma segura el resto de funcionalidades del frontend.

**Independent Test**: Se valida con un empleado activo que tenga email registrado, verificando acceso exitoso y rechazo con credenciales inválidas.

**Acceptance Scenarios**:

1. **Given** un empleado activo con email y contraseña válidos, **When** envía sus credenciales en el formulario de login, **Then** el sistema concede acceso a la interfaz protegida.
2. **Given** un email inexistente o contraseña incorrecta, **When** intenta iniciar sesión, **Then** el sistema rechaza el acceso y muestra un mensaje de error claro.
3. **Given** un empleado inactivo, **When** intenta iniciar sesión con credenciales correctas, **Then** el sistema rechaza el acceso.

---

### User Story 2 - Gestionar empleados desde frontend (Priority: P2)

Como usuario master, quiero crear, consultar, actualizar y eliminar empleados desde una interfaz web para gestionar el personal sin usar llamadas manuales.

**Why this priority**: Entrega el valor principal solicitado: un CRUD usable de empleados en frontend.

**Independent Test**: Se valida ejecutando el flujo completo de alta, edición, consulta y eliminación lógica de empleados desde la interfaz.

**Acceptance Scenarios**:

1. **Given** un usuario autenticado identificado como master, **When** crea un empleado con datos válidos (incluyendo email), **Then** el sistema registra al empleado y lo muestra en el listado.
2. **Given** un usuario autenticado identificado como master y un empleado existente, **When** actualiza sus datos, **Then** el sistema refleja los cambios en la vista y en consultas posteriores.
3. **Given** un usuario autenticado identificado como master y un empleado existente, **When** lo elimina, **Then** el sistema lo marca como inactivo y deja de mostrarlo en listados operativos.
4. **Given** un usuario autenticado no master, **When** ingresa al módulo de empleados, **Then** el sistema permite consulta pero deniega crear, editar y eliminar.

---

### User Story 3 - Usar email como dato obligatorio de empleado (Priority: P3)

Como usuario master, quiero capturar y mantener el email de cada empleado para habilitar su autenticación y contacto operativo.

**Why this priority**: La autenticación solicitada depende de que el email exista y sea consistente en el registro de empleados.

**Independent Test**: Se valida intentando crear/actualizar empleados con email válido, inválido o duplicado, y verificando respuestas esperadas.

**Acceptance Scenarios**:

1. **Given** un usuario autenticado, **When** registra un empleado con email válido y no usado, **Then** el sistema acepta la operación.
2. **Given** un usuario autenticado, **When** registra o actualiza un empleado con email duplicado, **Then** el sistema rechaza la operación por conflicto.
3. **Given** un usuario autenticado, **When** registra o actualiza un empleado con formato de email inválido, **Then** el sistema rechaza la operación por validación.

---

### Edge Cases

<!--
  ACTION REQUIRED: The content in this section represents placeholders.
  Fill them out with the right edge cases.
-->

- Intento de login con email vacío o contraseña vacía.
- Intento de crear empleado sin email o con email inválido.
- Intento de crear/actualizar empleado con email ya registrado en otro empleado.
- Sesión expirada durante una operación de CRUD en frontend.
- Error de conectividad entre frontend y API durante guardar o eliminar.
- Respuestas de API con errores de validación o conflicto que deben mostrarse al usuario sin perder datos del formulario.
- Usuario autenticado no master intentando operaciones de escritura.

## Requirements *(mandatory)*

<!--
  ACTION REQUIRED: The content in this section represents placeholders.
  Fill them out with the right functional requirements.
-->

### Functional Requirements

- **FR-001**: El sistema MUST mostrar una pantalla de inicio de sesión que solicite email y contraseña.
- **FR-002**: El sistema MUST autenticar el acceso usando email + contraseña de un empleado activo.
- **FR-003**: El sistema MUST rechazar credenciales inválidas con mensaje de error entendible para el usuario.
- **FR-004**: El sistema MUST permitir cerrar sesión y proteger las vistas de CRUD cuando no exista sesión válida.
- **FR-005**: El sistema MUST incluir el campo `email` en el registro de empleado como dato obligatorio.
- **FR-006**: El sistema MUST validar formato de email al crear y actualizar empleados.
- **FR-007**: El sistema MUST garantizar unicidad de email entre empleados.
- **FR-008**: El sistema MUST permitir crear empleados desde la interfaz incluyendo nombre, teléfono, email y contraseña.
- **FR-009**: El sistema MUST permitir listar empleados activos en una vista de consulta.
- **FR-010**: El sistema MUST permitir actualizar nombre, teléfono y email de un empleado existente.
- **FR-011**: El sistema MUST permitir eliminar empleados de forma lógica desde la interfaz.
- **FR-012**: El sistema MUST mostrar mensajes de validación y conflicto devueltos por la API durante operaciones de CRUD.
- **FR-013**: El sistema MUST operar con rutas de API versionadas y consistentes con el contrato vigente del backend.
- **FR-014**: El sistema MUST mantener trazabilidad mínima de eventos de autenticación y operaciones de mantenimiento de empleados.
- **FR-015**: El sistema MUST permitir que cualquier usuario autenticado acceda al módulo de empleados en modo consulta.
- **FR-016**: El sistema MUST restringir las operaciones de creación, edición y eliminación de empleados únicamente al usuario master.
- **FR-017**: El sistema MUST bloquear y notificar intentos de operaciones de escritura por usuarios autenticados no master.
- **FR-018**: El sistema MUST determinar privilegios de usuario master mediante una configuración o tabla separada del registro de `Empleado`.

### Key Entities *(include if feature involves data)*

- **Empleado**: Representa al usuario gestionado por el sistema con identidad operativa; incluye `clave`, `nombre`, `telefono`, `email`, estado de actividad y credenciales de acceso.
- **Sesión de Acceso**: Representa el estado de autenticación del usuario en frontend para permitir o negar acceso a vistas protegidas.
- **Configuración Master**: Representa la fuente autorizada (tabla o configuración dedicada) para identificar qué usuario autenticado posee privilegios de escritura en CRUD.

## Assumptions & Dependencies

- Existe un servicio backend disponible que expone autenticación y operaciones de empleados con versionado estable.
- El mantenimiento de empleados (alta, edición y baja lógica) se realiza únicamente por el usuario master.
- La identificación del usuario master no depende de atributos del registro `Empleado`, sino de una fuente separada administrada por el sistema.
- La política operativa de eliminación de empleados es baja lógica (inactivación), no borrado físico inmediato.
- La operación requiere conectividad de red entre la interfaz de usuario y el backend durante login y CRUD.
- La experiencia objetivo es de uso interno administrativo, no autoservicio público.

## Success Criteria *(mandatory)*

<!--
  ACTION REQUIRED: Define measurable success criteria.
  These must be technology-agnostic and measurable.
-->

### Measurable Outcomes

- **SC-001**: El 100% de usuarios con credenciales válidas puede completar login en menos de 30 segundos.
- **SC-002**: El 100% de operaciones CRUD de empleados desde frontend (crear, listar, actualizar, eliminar) se ejecuta correctamente en pruebas de aceptación.
- **SC-003**: El 100% de intentos con email inválido o duplicado es rechazado con mensaje de validación o conflicto.
- **SC-004**: Al menos el 90% de usuarios de prueba completa el flujo login + alta de empleado sin asistencia.
- **SC-005**: El 100% de vistas de mantenimiento de empleados queda inaccesible sin sesión válida.
