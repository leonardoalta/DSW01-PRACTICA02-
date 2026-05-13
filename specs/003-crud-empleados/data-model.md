# Data Model - CRUD de Empleados

## Entity: Empleado

### Fields
- `clave` (string, required, unique)
  - Regla: no nulo, no vacío, clave primaria de negocio.
- `nombre` (string, required, maxLength: 100)
  - Regla: no nulo, longitud entre 1 y 100.
- `telefono` (string, required, maxLength: 100)
  - Regla: no nulo, longitud entre 1 y 100.
- `activo` (boolean, required, default: true)
  - Regla: define disponibilidad operativa del empleado.
- `createdAt` (datetime, required)
- `updatedAt` (datetime, required)

### Relationships
- No requiere relaciones con otras entidades en este alcance.

### Validation Rules
- Unicidad de `clave` obligatoria.
- `nombre` y `telefono` rechazan valores con longitud > 100.
- Operaciones de actualización/eliminación requieren existencia previa por `clave`.
- Eliminación lógica sobre registro ya inactivo retorna conflicto de estado.
- Si `page` se envía en listado, debe ser entero >= 0.
- Tamaño de página es fijo de 5 cuando aplica modo paginado.

### State Transitions
- `ACTIVO` (`activo=true`) → `INACTIVO` (`activo=false`) al eliminar lógicamente.
- No se define restauración en esta iteración.

## Entity: CredencialAcceso (contextual)

### Fields
- `usuario` (string, required)
- `password` (string, required)
- `roles` (string array, required)

### Notes
- Entidad contextual de autenticación para acceso al CRUD; no forma parte del dominio persistente de empleados en esta feature.

## Value Object: EmpleadoPage (respuesta paginada)

### Fields
- `content` (array de `Empleado`, required)
- `page` (integer, required, >= 0)
- `size` (integer, required, valor fijo 5)
- `totalElements` (integer, required, >= 0)
- `totalPages` (integer, required, >= 0)

### Usage Rules
- Solo se usa cuando la solicitud de listado incluye parámetro de paginación.
- En modo no paginado, la respuesta es colección completa de empleados activos sin metadatos.
