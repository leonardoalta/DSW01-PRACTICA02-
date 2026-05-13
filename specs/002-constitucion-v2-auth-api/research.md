# Phase 0 Research - Ajuste a Constitución v2.1 (PK autogenerada)

## Decision 1: Estrategia de generación de `clave` (PK)
- Decision: `clave` se autogenera en backend durante create y no se recibe del cliente.
- Rationale: cumple la aclaración funcional más reciente y elimina colisiones de entrada manual.
- Alternatives considered:
  - Mantener `clave` como input obligatorio: contradice la aclaración de negocio.
  - Generar `clave` en cliente: pierde control centralizado y trazabilidad.

## Decision 2: Formato de `clave` generada
- Decision: usar identificador determinístico de backend compatible con URL y autenticación (string canónica).
- Rationale: preserva compatibilidad con endpoints `/empleados/{clave}` y Basic Auth por `clave`.
- Alternatives considered:
  - Cambiar a PK numérica pura y separar username: mayor impacto transversal fuera de alcance inmediato.

## Decision 3: Identificador de autenticación de Empleado
- Decision: `clave` autogenerada sigue siendo el identificador de login de `Empleado`.
- Rationale: mantiene alineación con Constitución v2.1 y evita introducir un segundo identificador.
- Alternatives considered:
  - Campo nuevo `usuario`: añade migración y lógica adicional sin necesidad para este feature.

## Decision 4: Almacenamiento seguro de contraseña
- Decision: persistir `contrasenaHash` con `BCryptPasswordEncoder`.
- Rationale: estándar en Spring Security, costo configurable, sin persistir secretos en claro.
- Alternatives considered:
  - SHA-256 directo: inadecuado para contraseñas.
  - Argon2: válido, pero incrementa complejidad operativa para este alcance.

## Decision 5: Contrato API para create
- Decision: `POST /api/v1/empleados` acepta `nombre`, `telefono`, `contrasena` y retorna `clave` generada.
- Rationale: materializa la regla de negocio en contrato consumible por clientes.
- Alternatives considered:
  - Aceptar `clave` opcional y sobrescribir: comportamiento ambiguo y propenso a errores.

## Decision 6: Versionado y compatibilidad
- Decision: mantener rutas versionadas `/api/v1/...` sin cambios de namespace.
- Rationale: el cambio es semántico de payload/modelo, no de versión de ruta.
- Alternatives considered:
  - Mover a `/api/v2`: innecesario para ajuste interno no disruptivo de cliente si contrato se actualiza en v1.

## Decision 7: Cobertura de pruebas mínima requerida
- Decision: incluir pruebas de integración para create sin `clave`, generación de `clave`, seguridad, CRUD y paginación.
- Rationale: valida contrato, persistencia y comportamiento observable end-to-end.
- Alternatives considered:
  - Solo pruebas unitarias: insuficientes para validar contrato HTTP y wiring de seguridad.
