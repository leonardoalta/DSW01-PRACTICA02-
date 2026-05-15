# Implementation Plan: Frontend de Empleados con Login por Email

**Branch**: `001-frontend-login-empleados` | **Date**: 2026-03-11 | **Spec**: `specs/001-frontend-login-empleados/spec.md`
**Input**: Feature specification from `/specs/001-frontend-login-empleados/spec.md`

## Summary

Implementar una aplicación frontend Angular para autenticación de empleados por `email + contrasena` y gestión CRUD de empleados, manteniendo autorización diferenciada: cualquier autenticado puede consultar y solo el usuario master (definido fuera de `Empleado`) puede crear/editar/eliminar. El backend Spring Boot 3/Java 17 se mantiene como proveedor de API versionada (`/api/v1`) con contrato OpenAPI actualizado para incluir `email` obligatorio y único.

## Technical Context

**Language/Version**: Java 17 (backend), TypeScript 5.x (frontend Angular LTS)  
**Primary Dependencies**: Spring Boot 3.x, Spring Security, Spring Data JPA, Angular 19 LTS, RxJS, Angular Router, Angular HttpClient  
**Storage**: PostgreSQL (entorno local/integración en Docker), H2/Testcontainers para pruebas backend  
**Testing**: Maven Surefire/Failsafe (JUnit + integración), Angular unit tests (`npm test`) y pruebas manuales de flujo  
**Target Platform**: Linux server para backend + navegador moderno para frontend web  
**Project Type**: Web application (backend API + frontend SPA)  
**Performance Goals**: Login exitoso en <30s percibidos y operaciones CRUD con respuesta interactiva en entorno interno  
**Constraints**: Autenticación canónica por `Empleado.email`, `clave` se conserva como PK de dominio; operaciones de escritura solo para master; API versionada obligatoria  
**Scale/Scope**: Uso administrativo interno (decenas a cientos de empleados), una SPA Angular y endpoints `/api/v1/empleados`

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

### Pre-Design Gate Evaluation

- Stack gate: PASS — backend definido sobre Spring Boot 3 + Java 17.
- Frontend stack gate: PASS — UI definida en Angular LTS.
- Data model identity gate: PASS — `clave` permanece PK y `email` es identificador canónico de autenticación.
- Security gate: PASS — autenticación por entidad `Empleado`, contraseña segura y controles de autorización por master.
- API versioning gate: PASS — consumo y contrato en `/api/v1/...`.
- Data gate: PASS — PostgreSQL en Docker para entorno local/integración.
- API contract gate: PASS — contrato OpenAPI actualizado para login por email y CRUD.
- Quality gate: PASS — estrategia de pruebas backend/frontend definida.

### Post-Design Gate Re-Check

- Stack gate: PASS — sin desviaciones de stack.
- Frontend stack gate: PASS — contratos y quickstart mantienen Angular LTS.
- Data model identity gate: PASS — `data-model.md` conserva `clave` como PK y `email` como login canónico.
- Security gate: PASS — contratos documentan `401/403`, bloqueo de escritura para no-master y sesión válida obligatoria.
- API versioning gate: PASS — `contracts/empleados-auth-v1.openapi.yaml` usa `/api/v1`.
- Data gate: PASS — `quickstart.md` conserva PostgreSQL + Docker Compose.
- API contract gate: PASS — contratos API/UI generados y alineados con requisitos.
- Quality gate: PASS — flujo de validación incluye `mvn test` y `npm test`.

## Project Structure

### Documentation (this feature)

```text
specs/001-frontend-login-empleados/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   ├── empleados-auth-v1.openapi.yaml
│   └── frontend-ui-contract.md
└── tasks.md
```

### Source Code (repository root)

```text
src/
└── main/
    ├── java/
    └── resources/

test/
└── java/

frontend/                    # a crear en implementación
├── src/
│   ├── app/
│   │   ├── auth/
│   │   ├── empleados/
│   │   └── core/
│   └── environments/
└── package.json

docker/
└── docker-compose.yml
```

**Structure Decision**: Se adopta estructura web application con backend Spring existente en raíz del repositorio y SPA Angular en carpeta `frontend/`, manteniendo contratos en `specs/001-frontend-login-empleados/contracts/`.

## Complexity Tracking

No constitutional violations detected; no exemptions required.
