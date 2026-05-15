<!--
Sync Impact Report
- Version change: 2.1.0 → 2.2.0
- Modified principles:
	- I. Stack Baseline Mandatorio → I. Stack Baseline Mandatorio (Backend + Frontend)
- Added sections:
	- Ninguna
- Removed sections:
	- Ninguna
- Templates requiring updates:
	- ✅ .specify/templates/plan-template.md
	- ✅ .specify/templates/spec-template.md
	- ✅ .specify/templates/tasks-template.md
	- ✅ .github/agents/copilot-instructions.md
	- ⚠ pending: .specify/templates/commands/*.md (directorio no existe)
- Follow-up TODOs:
	- TODO(COMMAND_TEMPLATES): Crear .specify/templates/commands/ para validar referencias de comandos cuando el proyecto lo habilite.
-->

# Ejercicio3 Platform Constitution

## Core Principles

### I. Stack Baseline Mandatorio (Backend + Frontend)
Toda implementación de backend MUST usar Spring Boot 3 con Java 17. Toda
implementación de frontend web MUST usar Angular en su versión LTS vigente al
inicio del feature. Cambios de framework principal o versión LTS del lenguaje
requieren enmienda de esta constitución.
Rationale: mantener una base técnica única por capa reduce complejidad
operativa, inconsistencias de build y deuda de mantenimiento.

### II. Seguridad por Defecto con Identidad Empleado y Email Canónico
Todo endpoint no público MUST protegerse con autenticación basada en la entidad
`Empleado`. El modelo de identidad MUST incluir el campo `contrasena` y su persistencia
segura (nunca en texto plano). La propiedad `email` del `Empleado` MUST ser el identificador
canónico de autenticación y debe ser único y obligatorio. El backend MUST autenticar contra
empleados activos usando el campo `email` y MUST rechazar credenciales inválidas con respuestas
consistentes.
Rationale: usar email como identificador canónico alinea la autenticación con la experiencia
moderna y elimina credenciales hardcodeadas, fortaleciendo trazabilidad y control de acceso.

### III. Persistencia PostgreSQL en Docker
La base de datos del proyecto MUST ser PostgreSQL y su ejecución local/integración MUST
gestionarse con Docker (contenedor o compose). La aplicación MUST configurarse por
propiedades de entorno para host, puerto, base de datos, usuario y contraseña.
Rationale: estandarizar persistencia y entorno elimina diferencias entre máquinas y
mejora reproducibilidad de despliegues.

### IV. Versionado Obligatorio de API y Contrato OpenAPI
Toda URL de API HTTP MUST estar versionada explícitamente (por ejemplo, prefijo
`/api/v1/...`). Toda documentación OpenAPI/Swagger MUST reflejar ese versionado y estar
sincronizada con el comportamiento real de controladores, modelos y errores.
Endpoints nuevos o modificados MUST actualizar su contrato en el mismo cambio.
Rationale: versionar rutas reduce rupturas para consumidores y permite evolución
controlada del servicio.

### V. Calidad Automatizada y Entregabilidad
Cada cambio MUST incluir pruebas automáticas proporcionales al riesgo (mínimo unitarias
y, cuando aplique, integración de seguridad/persistencia). El pipeline MUST validar
compilación, tests y configuración sin credenciales hardcodeadas fuera de valores de
desarrollo aprobados por esta constitución.
Rationale: calidad verificable y builds repetibles reducen regresiones y fallos tardíos.

## Restricciones Técnicas Obligatorias

- Runtime objetivo MUST ser Java 17.
- Framework web MUST ser Spring Boot 3.x.
- Frontend web MUST ser Angular en versión LTS vigente al inicio del feature.
- Persistencia relacional MUST ser PostgreSQL.
- Aprovisionamiento local de base de datos MUST ejecutarse en Docker.
- Entidad `Empleado` MUST usar `clave` como PK de persistencia y `email` como identificador canónico de login.
- Autenticación MUST usar la entidad `Empleado` con atributo `contrasena`.
- Rutas HTTP de API MUST publicarse con versión en la URL (`/api/v{n}/...`).
- Documentación API MUST publicarse mediante OpenAPI/Swagger.
- Frontend MUST consumir APIs versionadas (`/api/v{n}/...`) y mantener contrato
	de integración trazable por ambiente.
- Configuración de conexión y seguridad SHOULD externalizarse con variables de entorno
	y perfiles de Spring.

## Flujo de Desarrollo y Puertas de Calidad

1. Toda especificación de feature MUST declarar impacto en seguridad, persistencia,
	versionado de rutas API, contratos API, observabilidad y (si aplica) frontend Angular.
2. Todo plan técnico MUST pasar Constitution Check con evidencias concretas de:
	stack backend/frontend, `clave` como PK de `Empleado`, autenticación por entidad
	`Empleado` usando `email` como identificador canónico, versionado de URL de API,
	PostgreSQL en Docker, documentación Swagger y estrategia de test.
3. Todo conjunto de tareas MUST incluir trabajo explícito para configuración de entorno,
	seguridad, versionado de rutas, documentación API, pruebas automatizadas y,
	cuando aplique, setup de Angular LTS y validación de integración frontend-backend.
4. Todo PR MUST demostrar ejecución satisfactoria de build y tests relevantes antes de merge.

## Governance

Esta constitución prevalece sobre prácticas locales no documentadas.
Las enmiendas MUST incluir: motivación, impacto, tipo de cambio semántico de versión y
actualización de plantillas afectadas.

Política de versionado de la constitución:
- MAJOR: eliminación o redefinición incompatible de principios o gobernanza.
- MINOR: adición de principio o expansión material de obligaciones.
- PATCH: aclaraciones editoriales sin cambio normativo.

Revisión de cumplimiento:
- En planeación: verificación obligatoria del Constitution Check.
- En implementación: trazabilidad entre tareas y principios aplicables.
- En revisión: rechazo de cambios que incumplan principios no exentos.

**Version**: 3.0.0 | **Ratified**: 2026-03-05 | **Last Amended**: 2026-03-11
