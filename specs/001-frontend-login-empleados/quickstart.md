# Quickstart - Frontend Empleados + Login Email

## Prerrequisitos
- Java 17
- Maven 3.9+
- Node.js 20 LTS+
- npm 10+
- Docker y Docker Compose

## 1) Levantar PostgreSQL en Docker

```bash
docker compose -f docker/docker-compose.yml up -d
```

## 2) Configurar backend

```bash
export DB_HOST=localhost
export DB_PORT=5432
export DB_NAME=ejercicio3
export DB_USER=postgres
export DB_PASSWORD=postgres
mvn spring-boot:run
```

## 3) Crear frontend Angular (si no existe aún)

```bash
npx @angular/cli@19 new frontend --routing --style=scss
cd frontend
npm install
```

## 4) Configurar entorno frontend

Definir `apiBaseUrl` apuntando a `http://localhost:8080/api/v1` en `frontend/src/environments/`.

## 5) Ejecutar frontend

```bash
cd frontend
npm start
```

## 6) Flujo de validación manual

1. Abrir UI de login.
2. Ingresar `email` + `contrasena` de empleado activo.
3. Verificar acceso a listado de empleados.
4. Crear empleado con `email` único.
5. Editar empleado sin enviar `contrasena` y confirmar que la contraseña no cambia.
6. Eliminar empleado y validar que desaparece del listado operativo.
7. Iniciar sesión con usuario no master y confirmar denegación de acciones de escritura.
8. Iniciar sesión con usuario master y confirmar habilitación de crear/editar/eliminar.

## 7) Pruebas automáticas

Backend:

```bash
mvn test
```

Frontend:

```bash
cd frontend
npm test
```

## 8) Apagar infraestructura

```bash
docker compose -f docker/docker-compose.yml down -v
```

Si backend/frontend fueron iniciados fuera de Docker:

```bash
pkill -f "spring-boot:run|Ejercicio3Application" || true
pkill -f "ng serve" || true
docker rm -f empleados-postgres 2>/dev/null || true
```

## 9) Troubleshooting recomendado

### A) `docker compose` falla por versión/API

Síntoma:
- Error tipo `client version ... is too old`.

Acción:
- Actualizar plugin de Docker Compose.
- Workaround temporal para BD:

```bash
docker rm -f empleados-postgres 2>/dev/null || true
docker volume create postgres_data >/dev/null
docker run -d --name empleados-postgres \
	-e POSTGRES_DB=ejercicio3 \
	-e POSTGRES_USER=postgres \
	-e POSTGRES_PASSWORD=postgres \
	-p 5432:5432 \
	-v postgres_data:/var/lib/postgresql/data \
	postgres:16
```

### B) Login del master devuelve "Credenciales inválidas"

Síntoma:
- `master@ejercicio3.local` / `admin123` falla en frontend.

Causa común:
- La BD no tenía los datos seed cargados.

Verificación rápida:

```bash
curl -i -u master@ejercicio3.local:admin123 http://localhost:8080/api/v1/empleados?page=0
```

Debe responder `200`.

Prevención:
- Mantener `spring.sql.init.mode=always` en `application.properties` para entornos locales con PostgreSQL.
- Reiniciar backend después de cambios de configuración o de una recreación de BD.

### C) Frontend en blanco

Causa común:
- Faltan polyfills de `zone.js` en configuración Angular.

Prevención:
- Confirmar en `frontend/angular.json`:
	- build: `polyfills: ["zone.js"]`
	- test: `polyfills: ["zone.js", "zone.js/testing"]`

### D) `favicon.ico 404` en consola

Nota:
- Este `404` no bloquea el login ni el render principal.
- Puede ignorarse o resolverse agregando un favicon al frontend.
