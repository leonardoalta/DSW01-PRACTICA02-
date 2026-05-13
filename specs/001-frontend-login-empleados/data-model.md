# Data Model - Frontend Empleados + Login Email

## Entity: Empleado

### Fields
- `clave` (string, required, unique, immutable)
  - Regla: clave primaria canónica del dominio.
- `nombre` (string, required, maxLength: 100)
- `telefono` (string, required, maxLength: 100)
- `email` (string, required, unique, maxLength: 150)
  - Regla: formato de email válido.
- `contrasena` (string, required on create, optional on update)
  - Regla: persistencia segura (hash, nunca texto plano).
- `activo` (boolean, required, default: true)
- `departamentoId` (long, optional)
- `createdAt` (datetime, required)
- `updatedAt` (datetime, required)

### Relationships
- `Empleado` N:1 `Departamento` (opcional).

### Validation Rules
- `email` obligatorio y único en altas/actualizaciones.
- Rechazar actualización cuando `email` ya pertenece a otro empleado.
- Crear empleado requiere `contrasena`; actualizar empleado no la requiere.
- Cualquier usuario autenticado puede acceder a la vista de consulta.
- Solo el usuario master (resuelto desde configuración/tabla separada) puede crear/editar/eliminar empleados.
- Eliminación lógica: `activo` pasa de `true` a `false`.

### State Transitions
- `ACTIVO` -> `INACTIVO` al eliminar lógicamente.
- `INACTIVO` no puede autenticarse.

## Entity: SessionState (frontend)

### Fields
- `isAuthenticated` (boolean, required)
- `principalEmail` (string, required when authenticated)
- `roles` (string[], required when authenticated)
- `lastAuthAt` (datetime, optional)

### Usage Rules
- Si `isAuthenticated=false`, rutas de mantenimiento quedan bloqueadas.
- Si el usuario autenticado no es master, UI deniega acciones de escritura.

## Entity: MasterAccessConfig

### Fields
- `id` (string, required)
- `masterPrincipal` (string, required)
- `enabled` (boolean, required, default: true)

### Usage Rules
- `masterPrincipal` identifica al único usuario con permisos de escritura.
- Si `enabled=false` o la configuración no existe, todas las operaciones de escritura quedan bloqueadas.

## Value Object: EmpleadoForm

### Fields
- `nombre` (string, required)
- `telefono` (string, required)
- `email` (string, required)
- `contrasena` (string, required on create, optional on update)

### UX Rules
- En alta: `contrasena` visible como campo obligatorio.
- En edición: `contrasena` opcional; si está vacía no modifica la actual.
