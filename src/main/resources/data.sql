INSERT INTO empleados (clave, nombre, telefono, email, contrasena_hash, activo, created_at, updated_at)
VALUES (
  'AUTH_EMP',
  'Empleado Autenticador',
  '0000000000',
  'master@ejercicio3.local',
  '$2y$10$/r.J4KH/IXWOqFPxlADQSu40nXQiZXmvpt5I5WreSe0ASfLrqAoxS',
  TRUE,
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
)
ON CONFLICT (clave) DO NOTHING;

INSERT INTO master_access_config (id, master_principal, enabled)
VALUES ('MASTER', 'master@ejercicio3.local', TRUE)
ON CONFLICT (id) DO NOTHING;

INSERT INTO departamentos (nombre, descripcion, created_at, updated_at)
VALUES (
  'General',
  'Departamento inicial de ejemplo',
  CURRENT_TIMESTAMP,
  CURRENT_TIMESTAMP
)
ON CONFLICT (nombre) DO NOTHING;
