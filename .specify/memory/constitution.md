<!--
Sync Impact Report
- Version change: 0.0.0 → 1.0.0
- Modified principles:
	- template placeholder principle 1 → I. Stack Baseline Mandatorio
	- template placeholder principle 2 → II. Seguridad por Defecto con Basic Auth
	- template placeholder principle 3 → III. Persistencia PostgreSQL en Docker
	- template placeholder principle 4 → IV. Contrato API y Documentación OpenAPI
	- template placeholder principle 5 → V. Calidad Automatizada y Entregabilidad
- Added sections:
	- Restricciones Técnicas Obligatorias
	- Flujo de Desarrollo y Puertas de Calidad
- Removed sections:
	- Ninguna
- Templates requiring updates:
	- ✅ .specify/templates/plan-template.md
	- ✅ .specify/templates/spec-template.md
	- ✅ .specify/templates/tasks-template.md
	- ⚠ pending: .specify/templates/commands/*.md (directorio no existe)
- Follow-up TODOs:
	- TODO(COMMAND_TEMPLATES): Crear .specify/templates/commands/ para validar referencias de comandos cuando el proyecto lo habilite.
-->

# Ejercicio3 Backend Constitution

## Core Principles

### I. Stack Baseline Mandatorio
Toda implementación de backend MUST usar Spring Boot 3 con Java 17. Cambios de
framework principal o versión LTS del lenguaje requieren enmienda de esta constitución.
Rationale: mantener una base técnica única reduce complejidad operativa,
inconsistencias de build y deuda de mantenimiento.

### II. Seguridad por Defecto con Basic Auth
Todo endpoint no público MUST protegerse con autenticación HTTP Basic en Spring Security.
Para entornos de desarrollo y documentación interactiva, las credenciales base son
usuario `admin` y contraseña `admin123`; en producción MUST reemplazarse por secretos
gestionados externamente. Rationale: un baseline de seguridad explícito evita APIs
anónimas por error y permite verificación temprana del control de acceso.

### III. Persistencia PostgreSQL en Docker
La base de datos del proyecto MUST ser PostgreSQL y su ejecución local/integración MUST
gestionarse con Docker (contenedor o compose). La aplicación MUST configurarse por
propiedades de entorno para host, puerto, base de datos, usuario y contraseña.
Rationale: estandarizar persistencia y entorno elimina diferencias entre máquinas y
mejora reproducibilidad de despliegues.

### IV. Contrato API y Documentación OpenAPI
Toda API HTTP MUST mantener documentación OpenAPI/Swagger accesible y sincronizada con
el comportamiento real de controladores, modelos y respuestas de error. Endpoints nuevos
o modificados MUST actualizar su contrato en el mismo cambio.
Rationale: el contrato vivo reduce fricción entre backend, QA y consumidores.

### V. Calidad Automatizada y Entregabilidad
Cada cambio MUST incluir pruebas automáticas proporcionales al riesgo (mínimo unitarias
y, cuando aplique, integración de seguridad/persistencia). El pipeline MUST validar
compilación, tests y configuración sin credenciales hardcodeadas fuera de valores de
desarrollo aprobados por esta constitución.
Rationale: calidad verificable y builds repetibles reducen regresiones y fallos tardíos.

## Restricciones Técnicas Obligatorias

- Runtime objetivo MUST ser Java 17.
- Framework web MUST ser Spring Boot 3.x.
- Persistencia relacional MUST ser PostgreSQL.
- Aprovisionamiento local de base de datos MUST ejecutarse en Docker.
- Documentación API MUST publicarse mediante OpenAPI/Swagger.
- Configuración de conexión y seguridad SHOULD externalizarse con variables de entorno
	y perfiles de Spring.

## Flujo de Desarrollo y Puertas de Calidad

1. Toda especificación de feature MUST declarar impacto en seguridad, persistencia,
	 contratos API y observabilidad.
2. Todo plan técnico MUST pasar Constitution Check con evidencias concretas de:
	 stack, autenticación, PostgreSQL en Docker, documentación Swagger y estrategia de test.
3. Todo conjunto de tareas MUST incluir trabajo explícito para configuración de entorno,
	 seguridad, documentación API y pruebas automatizadas.
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

**Version**: 1.0.0 | **Ratified**: 2026-03-05 | **Last Amended**: 2026-03-05
