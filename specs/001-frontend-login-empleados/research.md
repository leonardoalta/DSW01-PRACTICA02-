# Phase 0 Research - Frontend Empleados + Login Email

## Decision 1: Frontend framework baseline
- Decision: Implementar la UI con Angular LTS (adoptando Angular 19 LTS para este feature).
- Rationale: Cumple la constitución del proyecto y ofrece una base estable para autenticación, routing y formularios empresariales.
- Alternatives considered:
  - React: no cumple el principio constitucional de stack.
  - Vue: no cumple el principio constitucional de stack.

## Decision 2: Estrategia de autenticación del frontend
- Decision: Mantener autenticación HTTP Basic contra backend, usando `email` como identificador y `contrasena` como secreto.
- Rationale: Reutiliza el mecanismo de seguridad existente y minimiza cambios de infraestructura para el alcance actual.
- Alternatives considered:
  - JWT con endpoint de token: válido, pero agrega complejidad no requerida en esta iteración.
  - Sesión de servidor stateful con cookies: implica cambios adicionales en backend y CORS.

## Decision 3: Regla de autorización de mantenimiento
- Decision: Permitir acceso al módulo a cualquier usuario autenticado y restringir crear/editar/eliminar al usuario master definido en una fuente separada del registro `Empleado`.
- Rationale: Alinea la aclaración funcional confirmada, separa privilegios de escritura del modelo de identidad y reduce riesgo de modificaciones no autorizadas.
- Alternatives considered:
  - CRUD para cualquier autenticado: mayor riesgo operativo.
  - Master dentro de atributos de `Empleado`: mayor acoplamiento entre identidad y política operativa.

## Decision 4: Evolución del modelo Empleado con email
- Decision: Agregar `email` obligatorio, único y validado por formato en backend, reflejado en formularios de alta/edición en frontend.
- Rationale: El login solicitado depende de un identificador estable y único.
- Alternatives considered:
  - Email opcional: no garantiza autenticación por email para todos los empleados.
  - Reusar `clave` como login: contradice requisito explícito del usuario.

## Decision 5: Política de contraseña en edición
- Decision: En edición de empleado, `contrasena` será opcional y solo se actualiza cuando se envía un nuevo valor.
- Rationale: Reduce fricción operativa y evita reingresar secretos en cambios administrativos no relacionados.
- Alternatives considered:
  - Contraseña obligatoria siempre: empeora UX y eleva errores de operación.
  - Flujo separado exclusivo para contraseña: más seguro, pero fuera del alcance MVP actual.

## Decision 6: Contratos de integración frontend-backend
- Decision: Mantener contrato OpenAPI versionado para endpoints consumidos y agregar contrato de UI para flujos de login y CRUD.
- Rationale: Asegura trazabilidad funcional desde interfaz hasta API.
- Alternatives considered:
  - Solo documentación narrativa: menor precisión para pruebas y automatización.
  - Solo OpenAPI sin contrato UI: deja ambiguos estados de navegación y mensajes de error.
