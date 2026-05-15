# Implementation Plan: Ajuste CRUD Empleados a Constitución v2 (PK autogenerada)

**Branch**: `002-constitucion-v2-auth-api` | **Date**: 2026-03-06 | **Spec**: /specs/002-constitucion-v2-auth-api/spec.md
**Input**: Feature specification from `/specs/002-constitucion-v2-auth-api/spec.md`

## Summary

Actualizar el CRUD de empleados para cumplir Constitución v2.1: la `clave` de
`Empleado` será PK autogenerada por el sistema (no solicitada en alta), manteniendo
autenticación por entidad `Empleado`, versionado `/api/v1/...`, PostgreSQL en Docker,
OpenAPI sincronizado y pruebas automatizadas.

## Technical Context

**Language/Version**: Java 17  
**Primary Dependencies**: Spring Boot 3 (Web, Validation, Security, Data JPA), springdoc-openapi, PostgreSQL JDBC  
**Storage**: PostgreSQL (runtime) + H2 para pruebas de integración  
**Testing**: JUnit 5, Spring Boot Test, MockMvc  
**Target Platform**: Linux server / contenedor Docker  
**Project Type**: backend web-service monolítico  
**Performance Goals**: p95 < 2s en operaciones CRUD en entorno local de referencia  
**Constraints**: eliminación lógica, `nombre`/`telefono` <= 100, paginación opcional tamaño fijo 5, rutas `/api/v1`, `clave` PK autogenerada no enviada en create  
**Scale/Scope**: 1 dominio principal (`Empleado`) y 5 endpoints CRUD versionados  
**Login Identifier**: `clave` autogenerada de `Empleado`  
**PK Strategy**: generación automática de `clave` por backend al crear empleado (sin entrada de cliente)

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- **Pre-Phase-0 check**
  - Stack gate: PASS — Spring Boot 3 + Java 17 se mantiene.
  - Data model identity gate: PASS CON CLARIFICACIÓN — `clave` definida como PK autogenerada; falta cerrar formato/estrategia exacta.
  - Security gate: PASS — autenticación por `Empleado` activo y `contrasena` segura.
  - API versioning gate: PASS — rutas v1 obligatorias en contrato e implementación.
  - Data gate: PASS — PostgreSQL + Docker como baseline.
  - API contract gate: PASS — contrato se actualizará para create sin `clave` de entrada.
  - Quality gate: PASS — cobertura para create autogenerado + CRUD + seguridad.

- **Post-Phase-1 re-check**
  - Stack gate: PASS — diseño no altera stack base.
  - Data model identity gate: PASS — `data-model.md` define `clave` PK autogenerada y canónica.
  - Security gate: PASS — autenticación preserva `clave` como identificador.
  - API versioning gate: PASS — contrato mantiene `/api/v1/...`.
  - Data gate: PASS — persistencia PostgreSQL/Docker intacta.
  - API contract gate: PASS — contrato v1 alinea create sin `clave` y response con `clave` generada.
  - Quality gate: PASS — quickstart incluye validación de endpoints y pruebas.

## Project Structure

### Documentation (this feature)

```text
specs/002-constitucion-v2-auth-api/
├── plan.md
├── research.md
├── data-model.md
├── quickstart.md
├── contracts/
│   └── empleados-v1.openapi.yaml
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
        └── application.properties

src/
└── test/
    ├── java/com/ejercicio3/integration/
    └── resources/

docker/
└── docker-compose.yml
```

**Structure Decision**: se conserva estructura monolítica y se aplica refactor
incremental del modelo y contrato para autogenerar `clave` en create.

## Complexity Tracking

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| N/A | N/A | N/A |
