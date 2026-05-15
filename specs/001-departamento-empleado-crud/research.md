# Phase 0 Research - CRUD de Departamentos relacionados con Empleados

## Decision 1: Modelo de relación Departamento-Empleado
- Decision: Relación 1:N con referencia opcional `departamento_id` en `empleados`.
- Rationale: El feature requiere un solo departamento vigente por empleado; esta forma reduce complejidad y consultas de relación.
- Alternatives considered:
  - Tabla intermedia vigente: agrega complejidad sin necesidad funcional inmediata.
  - Tabla histórica de asignaciones: útil para auditoría avanzada, fuera de alcance MVP.

## Decision 2: Estrategia de PK para Departamento
- Decision: `id` numérico autogenerado (`BIGINT`) como PK técnica y `nombre` único como identificador de negocio.
- Rationale: Simplifica CRUD, join con empleados y evita colisiones de claves definidas por cliente.
- Alternatives considered:
  - `clave` string manual: aumenta validación y errores de captura.
  - UUID como PK: válido, pero innecesario para el alcance actual.

## Decision 3: Estrategia de eliminación de Departamento
- Decision: Eliminación física permitida solo cuando no existan empleados asociados.
- Rationale: El spec exige conflicto al intentar eliminar con asociaciones activas.
- Alternatives considered:
  - Eliminación lógica en departamento: agrega estados extra y no fue requerido explícitamente.

## Decision 4: Diseño de endpoints de relación
- Decision: Endpoint dedicado `PUT /api/v1/departamentos/{departamentoId}/empleados/{claveEmpleado}` para asignar/reasignar.
- Rationale: Hace explícita la intención de negocio y evita sobrecargar el CRUD de empleado.
- Alternatives considered:
  - Reasignar vía `PUT /api/v1/empleados/{clave}` con campo `departamentoId`: acopla dominios y difumina responsabilidades.

## Decision 5: Contrato de consulta de detalle
- Decision: El detalle de departamento incluye resumen del departamento y lista de empleados asociados.
- Rationale: Satisface FR-010 y reduce round-trips para casos de uso administrativos.
- Alternatives considered:
  - Solo IDs de empleados: insuficiente para consulta operativa.

## Decision 6: Reglas de validación de Departamento
- Decision: `nombre` obligatorio y único (max 100), `descripcion` opcional (max 255).
- Rationale: Consistencia con límites existentes y claridad para usuario final.
- Alternatives considered:
  - Sin `descripcion`: reduce utilidad del módulo.
  - Longitudes no acotadas: incrementa riesgo de datos inconsistentes.

## Decision 7: Cobertura mínima de pruebas
- Decision: Pruebas de integración para CRUD de departamento, conflicto de eliminación con empleados asociados, y asignación/reasignación.
- Rationale: Valida comportamiento observable completo del feature y relación entre entidades.
- Alternatives considered:
  - Solo unitarias: no cubren contrato HTTP ni persistencia real de la relación.
