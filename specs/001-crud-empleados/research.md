# Phase 0 Research - CRUD de Empleados

## Decision 1: Patrón de eliminación lógica
- Decision: Usar eliminación lógica en `Empleado` mediante atributo booleano `activo` y filtrar por defecto `activo=true` en consultas operativas.
- Rationale: Preserva historial, evita pérdida irreversible y alinea con la aclaración funcional aceptada.
- Alternatives considered:
  - Eliminación física: simple, pero pierde trazabilidad.
  - Estado múltiple (`ACTIVO/INACTIVO/ELIMINADO`): más flexible, pero complejidad innecesaria para alcance MVP.

## Decision 2: Identificador de negocio `clave`
- Decision: Persistir `clave` como texto obligatorio y único, sin restricción adicional de formato en esta iteración.
- Rationale: Respeta el alcance del spec (sin regla de formato explícita) y evita bloquear el desarrollo por validaciones no solicitadas.
- Alternatives considered:
  - Clave numérica: limita interoperabilidad con claves existentes alfanuméricas.
  - UUID forzado: agrega fricción operativa al usuario administrador.

## Decision 3: Pruebas de integración de persistencia
- Decision: Usar Testcontainers con PostgreSQL para pruebas de integración.
- Rationale: Garantiza paridad con el motor real y reduce falsos positivos frente a bases embebidas.
- Alternatives considered:
  - H2 en modo compatibilidad: más rápido, pero con divergencias SQL.
  - Mock de repositorios: útil en unitarias, insuficiente para validar mapeo/consultas reales.

## Decision 4: Seguridad de endpoints
- Decision: Proteger endpoints CRUD con HTTP Basic y validar rutas críticas con pruebas de acceso autorizado/no autorizado.
- Rationale: Cumple constitución y provee baseline de seguridad verificable.
- Alternatives considered:
  - Endpoints públicos con filtros posteriores: no cumple principio de seguridad por defecto.
  - JWT/OAuth2: sobre-diseño para el alcance actual.

## Decision 5: Contrato API y documentación
- Decision: Definir contrato OpenAPI 3.0 para endpoints CRUD de empleados, incluyendo códigos de error de validación, autenticación y estado no encontrado/conflicto.
- Rationale: Alinea implementación con documentación viva y acelera pruebas manuales/automatizadas.
- Alternatives considered:
  - Documentación ad-hoc en README: menor mantenibilidad y menor precisión contractual.

## Decision 6: Estrategia de paginación del listado
- Decision: Soportar listados en dos modos sobre el mismo endpoint `GET /api/empleados`:
  - Sin parámetros de paginación: retorna todos los empleados activos.
  - Con parámetros de paginación (`page`): aplica tamaño fijo de 5 registros por página y retorna metadatos.
- Rationale: Cumple aclaración funcional sin romper compatibilidad para consumidores que requieren listado completo.
- Alternatives considered:
  - Paginación obligatoria: reduce carga de respuesta, pero incumple el requisito de devolver todo cuando no se envía paginación.
  - Parámetro de tamaño configurable: más flexible, pero contradice restricción explícita de 5 registros por página.
