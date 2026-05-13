# Implementation Plan: CRUD de Departamentos relacionados con Empleados

**Branch**: `001-departamento-empleado-crud` | **Date**: 2026-03-10 | **Spec**: /specs/001-departamento-empleado-crud/spec.md
**Input**: Feature specification from `/specs/001-departamento-empleado-crud/spec.md`

## Summary

Implementar CRUD versionado de `Departamento` y la relación 1:N con `Empleado`
(referencia opcional `departamento_id` en `empleados`), preservando autenticación
por entidad `Empleado`, PostgreSQL en Docker, contrato OpenAPI y pruebas
automatizadas de integración.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot 3 (Web, Validation, Security, Data JPA), springdoc-openapi, PostgreSQL JDBC  
**Storage**: PostgreSQL (runtime) + H2 para pruebas de integración  
**Testing**: JUnit 5, Spring Boot Test, MockMvc  
**Target Platform**: Linux server / contenedor Docker  
**Project Type**: backend web-service monolítico  
**Performance Goals**: p95 < 2s para operaciones CRUD de departamento y consultas relacionadas en entorno local de referencia  
**Constraints**: rutas `/api/v1`, autenticación con `Empleado` activo, compatibilidad con modelo actual de `Empleado`, no romper CRUD de empleados existente  
**Scale/Scope**: 1 nueva entidad principal (`Departamento`), 1 relación 1:N (`Departamento`→`Empleado`), 7 endpoints aproximados (CRUD + asignación/reasignación)

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **Pre-Phase-0 check**
  - Stack gate: PASS — se mantiene Spring Boot 3 + Java 17.
  - Data model identity gate: PASS — `Empleado.clave` sigue siendo PK/canónica; `Departamento` usa PK propia.
  - Security gate: PASS — endpoints de departamento protegidos con autenticación por `Empleado` activo.
  - API versioning gate: PASS — diseño en `/api/v1/...`.
  - Data gate: PASS — persistencia PostgreSQL + Docker conservada.
  - API contract gate: PASS — se definirá contrato OpenAPI versionado para nuevos endpoints.
  - Quality gate: PASS — plan incluye pruebas de CRUD, relación y errores de conflicto/not-found.

- **Post-Phase-1 re-check**
  - Stack gate: PASS — diseño final no altera baseline técnico.
  - Data model identity gate: PASS — sin cambios incompatibles en identidad de `Empleado`.
  - Security gate: PASS — quickstart y contratos contemplan autenticación consistente.
  - API versioning gate: PASS — contratos y ejemplos quedan en `/api/v1/departamentos...`.
  - Data gate: PASS — modelo relacional implementable en PostgreSQL con FK opcional.
  - API contract gate: PASS — contrato incluye CRUD y asignación de empleado a departamento.
  - Quality gate: PASS — artefactos de diseño preparados para generación de tareas y tests.

## Project Structure

### Documentation (this feature)

```text
specs/001-departamento-empleado-crud/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── departamentos-v1.openapi.yaml
└── tasks.md
```

### Source Code (repository root)

```text
src/
└── main/
    ├── java/com/ejercicio3/
    │   ├── config/
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── model/
    │   ├── dto/
    │   └── exception/
    └── resources/

src/
└── test/
    ├── java/com/ejercicio3/integration/
    └── resources/

docker/
└── docker-compose.yml
```

**Structure Decision**: se mantiene arquitectura monolítica existente y se extiende
el dominio con `Departamento` y referencia opcional en `Empleado`.

## Complexity Tracking

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |
