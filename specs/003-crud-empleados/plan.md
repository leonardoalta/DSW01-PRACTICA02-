# Implementation Plan: CRUD de Empleados

**Branch**: `003-crud-empleados` | **Date**: 2026-03-05 | **Spec**: /specs/003-crud-empleados/spec.md
**Input**: Feature specification from `/specs/003-crud-empleados/spec.md`

**Note**: This template is filled in by the `/speckit.plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

Implementar un backend CRUD de empleados con eliminación lógica, autenticación
HTTP Basic, documentación OpenAPI/Swagger y persistencia PostgreSQL en Docker.
El listado debe soportar paginación opcional: si no se envían parámetros retorna
todos los activos; cuando se envían parámetros, el tamaño de página es 5.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot 3 (Web, Validation, Security, Data JPA), PostgreSQL Driver, springdoc-openapi  
**Storage**: PostgreSQL  
**Testing**: JUnit 5, Spring Boot Test, MockMvc, Testcontainers (PostgreSQL)  
**Target Platform**: Linux server (contenedorizable)  
**Project Type**: web-service backend monolítico  
**Performance Goals**: p95 < 2s para operaciones CRUD y listado paginado/no paginado en entorno local de referencia  
**Constraints**: Basic Auth obligatorio, eliminación lógica, `nombre`/`telefono` ≤ 100, paginación opcional con tamaño fijo 5 cuando se solicita  
**Scale/Scope**: 1 entidad principal (`Empleado`), 5 endpoints CRUD, 1 endpoint de listado con modo paginado/no paginado, 1 rol operativo (admin)

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **Pre-Phase-0 check**
  - Stack gate: PASS — baseline explícito Spring Boot 3 + Java 17.
  - Security gate: PASS — Basic Auth requerido en todo endpoint protegido.
  - Data gate: PASS — PostgreSQL y ejecución local con Docker definidos.
  - API contract gate: PASS — contrato OpenAPI para CRUD incluyendo listado paginado/no paginado.
  - Quality gate: PASS — pruebas unitarias/integración y validación de build definidas.

- **Post-Phase-1 re-check**
  - Stack gate: PASS — research, data model y quickstart mantienen stack constitucional.
  - Security gate: PASS — contrato y quickstart documentan Basic Auth con credenciales de desarrollo.
  - Data gate: PASS — modelo y quickstart confirman PostgreSQL en Docker.
  - API contract gate: PASS — `contracts/empleados.openapi.yaml` incluye parámetros y respuesta paginada.
  - Quality gate: PASS — quickstart incluye ejecución de tests y verificación de endpoints.

## Project Structure

### Documentation (this feature)

```text
specs/003-crud-empleados/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── empleados.openapi.yaml
└── tasks.md
```

### Source Code (repository root)
```text
src/
└── main/
    ├── java/.../
    │   ├── config/
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── model/
    │   └── dto/
    └── resources/
        └── application.properties

src/
└── test/
    └── java/.../
        ├── unit/
        └── integration/

docker/
└── docker-compose.yml
```

**Structure Decision**: Se adopta un backend único Spring Boot con capas
`controller/service/repository/model`, contrato OpenAPI en `specs/.../contracts`,
pruebas en `src/test` y PostgreSQL provisionado por Docker para desarrollo e integración.

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |
