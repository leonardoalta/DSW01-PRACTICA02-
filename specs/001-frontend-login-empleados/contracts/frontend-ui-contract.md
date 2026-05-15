# Frontend UI Contract - Login y CRUD de Empleados

## Scope
Define los flujos funcionales y estados de interfaz para autenticación por email y mantenimiento de empleados.

## Routes
- `/login`: formulario de email + contraseña.
- `/empleados`: listado de empleados activos (requiere sesión válida).
- `/empleados/nuevo`: alta de empleado (solo master).
- `/empleados/:clave/editar`: edición de empleado (solo master).

## Access Rules
- Usuario no autenticado:
  - Acceso permitido: `/login`.
  - Acceso denegado: cualquier ruta de mantenimiento (`/empleados*`).
- Usuario autenticado no master:
  - Puede navegar a vistas permitidas de lectura.
  - No puede ejecutar acciones de crear, editar o eliminar.
- Usuario autenticado identificado como master:
  - Puede ejecutar CRUD completo de empleados.

## Master Resolution
- El estado de master se resuelve desde una fuente separada del registro `Empleado` (configuración o tabla dedicada).
- Si la fuente master no está disponible o deshabilitada, las acciones de escritura permanecen bloqueadas.

## Form Contracts
### Login Form
- Fields:
  - `email` (required, email format)
  - `contrasena` (required)
- Submit result:
  - Success: redirección a `/empleados`.
  - Error `401`: mensaje "Credenciales inválidas".

### Empleado Form (Create)
- Fields:
  - `clave` (required)
  - `nombre` (required)
  - `telefono` (required)
  - `email` (required, unique, email format)
  - `contrasena` (required)
- Errors:
  - `400`: mostrar validaciones por campo.
  - `409`: mostrar conflicto por clave/email duplicado.

### Empleado Form (Update)
- Fields:
  - `nombre` (required)
  - `telefono` (required)
  - `email` (required, unique, email format)
  - `contrasena` (optional)
- Rules:
  - Si `contrasena` se omite, no se modifica la contraseña actual.
- Errors:
  - `400`, `409` y `403` deben presentarse con mensaje claro al usuario.

## Deletion Contract
- Acción de eliminar confirma inactivación lógica.
- Resultado exitoso: refresca listado sin el empleado inactivo.

## Backend Dependency
- Todas las operaciones consumen rutas versionadas bajo `/api/v1`.
- La sesión del frontend depende de autenticación exitosa contra backend.
