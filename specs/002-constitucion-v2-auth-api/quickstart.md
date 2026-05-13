# Quickstart - CRUD Empleados con Constitución v2

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

## 4) Verificar autenticación con entidad Empleado

- En entorno local, se inicializa `AUTH_EMP` mediante `src/main/resources/data.sql`.
- La autenticación HTTP Basic usa `clave` como usuario y la contraseña del empleado (`admin123` para `AUTH_EMP`).
- En el alta de empleados, `clave` se genera automáticamente y no se envía en el payload.

Ejemplo de alta (sin `clave` en request):

```bash
curl -u AUTH_EMP:admin123 -H "Content-Type: application/json" \
	-X POST http://localhost:8080/api/v1/empleados \
	-d '{"nombre":"Empleado Demo","telefono":"5511002200","contrasena":"PasswordDemo1"}'
```

Ejemplo:

```bash
curl -u AUTH_EMP:admin123 http://localhost:8080/api/v1/empleados
```

Verificar modo paginado (5 por página):

```bash
curl -u AUTH_EMP:admin123 "http://localhost:8080/api/v1/empleados?page=0"
```

## 5) Verificar documentación API

- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Confirmar que paths expuestos usen `/api/v1/...`

## 6) Ejecutar pruebas

```bash
mvn test
```

## 7) Apagar contenedor

```bash
DOCKER_API_VERSION=1.44 docker compose -f docker/docker-compose.yml down -v
```

## 8) Evidencia de validación

- Ejecutar `mvn test` valida seguridad por entidad `Empleado`, rutas `/api/v1`, CRUD, validaciones y paginación opcional.
