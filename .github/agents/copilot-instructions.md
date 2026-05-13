# ejercicio3 Development Guidelines

Auto-generated from all feature plans. Last updated: 2026-03-11

## Active Technologies
- Java 17 + Spring Boot 3 (Web, Validation, Security, Data JPA), springdoc-openapi, PostgreSQL JDBC (002-constitucion-v2-auth-api)
- PostgreSQL (runtime) + H2 para tests de integración en entorno local (002-constitucion-v2-auth-api)
- PostgreSQL (runtime) + H2 para pruebas de integración (002-constitucion-v2-auth-api)
- Angular LTS para frontend web cuando el feature incluya interfaz de usuario
- Java 17 (backend), TypeScript 5.x (frontend Angular LTS) + Spring Boot 3.3.x (Web, Security, Validation, JPA), Angular LTS (CLI, Router, HttpClient, Forms), PostgreSQL Driver, springdoc-openapi (001-frontend-login-empleados)
- PostgreSQL (runtime), H2/Testcontainers para pruebas backend (001-frontend-login-empleados)
- Java 17 (backend), TypeScript 5.x (frontend Angular LTS) + Spring Boot 3.x, Spring Security, Spring Data JPA, Angular 19 LTS, RxJS, Angular Router, Angular HttpClient (001-frontend-login-empleados)
- PostgreSQL (entorno local/integración en Docker), H2/Testcontainers para pruebas backend (001-frontend-login-empleados)

- Java 17 + Spring Boot 3 (Web, Validation, Security, Data JPA), PostgreSQL Driver, springdoc-openapi (001-crud-empleados)

## Project Structure

```text
backend/
frontend/
tests/
```

## Commands

# Add commands for Java 17

## Code Style

Java 17: Follow standard conventions
Angular: usar convenciones de Angular CLI y TypeScript estricto para código nuevo de frontend

## Recent Changes
- 001-frontend-login-empleados: Added Java 17 (backend), TypeScript 5.x (frontend Angular LTS) + Spring Boot 3.x, Spring Security, Spring Data JPA, Angular 19 LTS, RxJS, Angular Router, Angular HttpClient
- 001-frontend-login-empleados: Added Java 17 (backend), TypeScript 5.x (frontend Angular LTS) + Spring Boot 3.3.x (Web, Security, Validation, JPA), Angular LTS (CLI, Router, HttpClient, Forms), PostgreSQL Driver, springdoc-openapi
- 001-departamento-empleado-crud: Added Java 17 + Spring Boot 3 (Web, Validation, Security, Data JPA), springdoc-openapi, PostgreSQL JDBC


<!-- MANUAL ADDITIONS START -->
<!-- MANUAL ADDITIONS END -->
