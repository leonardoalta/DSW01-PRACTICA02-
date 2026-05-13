# Quickstart - CRUD de Departamentos relacionados con Empleados

## Prerrequisitos
- Java 17
- Maven 3.9+
- Docker y Docker Compose

## 1) Levantar PostgreSQL en Docker

```bash
DOCKER_API_VERSION=1.44 DB_PORT=5434 docker compose -f docker/docker-compose.yml up -d
```

## 2) Configurar variables de entorno

```bash
export DB_HOST=localhost
export DB_PORT=5434
export DB_NAME=ejercicio3
export DB_USER=postgres
export DB_PASSWORD=postgres
```

## 3) Ejecutar aplicación

```bash
mvn spring-boot:run
```

## 4) Credenciales base para pruebas manuales
- Usuario: `AUTH_EMP`
- Contraseña: `admin123`

## 5) Flujo básico de prueba

### Crear departamento

```bash
curl -u AUTH_EMP:admin123 -H "Content-Type: application/json" \
  -X POST http://localhost:8080/api/v1/departamentos \
  -d '{"nombre":"Finanzas","descripcion":"Departamento financiero"}'
```

### Listar departamentos

```bash
curl -u AUTH_EMP:admin123 http://localhost:8080/api/v1/departamentos
```

### Consultar detalle

```bash
curl -u AUTH_EMP:admin123 http://localhost:8080/api/v1/departamentos/1
```

### Asignar empleado a departamento

```bash
curl -u AUTH_EMP:admin123 -X PUT \
  http://localhost:8080/api/v1/departamentos/1/empleados/AUTH_EMP
```

### Eliminar departamento sin empleados asociados

```bash
curl -u AUTH_EMP:admin123 -X DELETE http://localhost:8080/api/v1/departamentos/1
```

## 6) Ejecutar pruebas

```bash
mvn test
```

## 7) Evidencia de validación

- Flujo CRUD y relación probado con tests de integración de departamentos:
  - alta, validación y duplicado de nombre
  - actualización y eliminación con/sin empleados asociados
  - listado y detalle con `cantidadEmpleados`
  - asignación y reasignación de empleado a departamento
- Resultado de ejecución local:

```text
Tests run: 35, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

## 8) Apagar contenedor

```bash
DOCKER_API_VERSION=1.44 docker compose -f docker/docker-compose.yml down -v
```
