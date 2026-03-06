# Quickstart - CRUD de Empleados

## Prerrequisitos
- Java 17
- Maven 3.9+
- Docker y Docker Compose

## 1) Levantar PostgreSQL en Docker

```bash
docker compose -f docker/docker-compose.yml up -d
```

## 2) Configurar variables de entorno

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=ejercicio3
export DB_USER=postgres
export DB_PASSWORD=postgres
```

## 3) Ejecutar aplicación

```bash
mvn spring-boot:run
```

## 4) Verificar autenticación
- Usuario: `admin`
- Contraseña: `admin123`

Ejemplo de verificación:

```bash
curl -u admin:admin123 http://localhost:8080/api/empleados
```

Verificar modo paginado (5 por página):

```bash
curl -u admin:admin123 "http://localhost:8080/api/empleados?page=0"
```

Comportamiento esperado:
- Sin `page`: retorna todos los empleados activos.
- Con `page`: retorna estructura paginada con `size=5`.

## 5) Revisar documentación API
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- Swagger UI: `http://localhost:8080/swagger-ui.html`

## 6) Ejecutar pruebas

```bash
mvn test
```

## 7) Apagar contenedor

```bash
docker compose -f docker/docker-compose.yml down -v
```

## 8) Resultado de validación local

- Se ejecutó `mvn test` sobre el proyecto y la suite finalizó correctamente.
- La cobertura de integración verifica seguridad, altas, consultas con/sin paginación,
  actualización y eliminación lógica.
