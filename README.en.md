# desafioMagalu

[Português](README.md) | [English](README.en.md)

Communication scheduling service built with Spring Boot, JPA, MySQL, OpenAPI, and Docker Compose.

## Executive Summary

This repository implements the Magalu backend challenge with focus on the `ms-agendamento` service, responsible for receiving communication scheduling requests and exposing APIs for retrieval and removal.

Project goals:

- accept scheduling requests through a REST API
- persist data in a model ready for future delivery lifecycle evolution
- expose clear contracts and API documentation
- keep quality visible through tests, coverage, and CI automation

Main scope:

- `ms-agendamento`: create, retrieve, list, and delete schedules
- `docs/architecture`: diagrams and architecture support material
- `.github/workflows`: build, test, and coverage automation

Architecture at a glance:

Client
	|
`ms-agendamento` REST API
	|
Domain and persistence layers
	|
MySQL

## Stack

- Java 17
- Spring Boot 4.0.3
- Spring Data JPA
- MySQL 8
- Flyway
- Springdoc OpenAPI
- Testcontainers
- Docker Compose
- GitHub Actions + Codecov

## Project Status

- main service concentrated in `ms-agendamento`
- build, test, and coverage pipelines published
- architecture documentation available under `docs/architecture`
- detailed roadmap available in [plano.md](plano.md)

## Build, Tests and Coverage

| Type | Status |
| --- | --- |
| Build | [![Build ms-agendamento](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml/badge.svg?branch=main)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Tests | [![Tests ms-agendamento](https://img.shields.io/github/actions/workflow/status/leoalmeida/desafioMagalu/workflow.yml?branch=main&label=tests-ms-agendamento)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Coverage | [![Codecov](https://codecov.io/gh/leoalmeida/desafioMagalu/branch/main/graph/badge.svg?flag=ms-agendamento)](https://codecov.io/gh/leoalmeida/desafioMagalu) [![JaCoCo](https://img.shields.io/badge/coverage-JaCoCo-blue)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |

JaCoCo reports are generated in CI and can also be produced locally at ms-agendamento/target/site/jacoco/.

## Table of Contents

- [Executive Summary](#executive-summary)
- [Stack](#stack)
- [Project Status](#project-status)
- [Build, Tests and Coverage](#build-tests-and-coverage)
- [Repository Modules](#repository-modules)
- [Ports and Integrations](#ports-and-integrations)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Configuration](#configuration)
- [Tests](#tests)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)
- [References](#references)

## Repository Modules

Main features:

- schedule communication delivery with date/time, recipient, message, type, and status
- retrieve a schedule by ID
- list schedules
- delete a schedule by ID

Main structure:

- `ms-agendamento`
- `docs/architecture`
- `.github/workflows`

## Ports and Integrations

- ms-agendamento: server.port=0 (dynamic port)
- MySQL (docker-compose): 3306 in container, mapped through MYSQL_LOCAL_PORT

## Requirements

- JDK 17+
- Maven 3.9+
- Docker (for full environment and E2E)

## Quick Start

### Essential local startup

1. run the module build
2. start MySQL with Docker Compose or point to an existing instance
3. start `ms-agendamento`
4. validate the endpoints and OpenAPI documentation

### 1. Full build

```powershell
Set-Location "c:\Users\leo_a\projetos\desafioMagalu"
mvn -B -f ms-agendamento/pom.xml clean package
```

### 2. Run application

```powershell
Set-Location "c:\Users\leo_a\projetos\desafioMagalu\ms-agendamento"
.\mvnw.cmd spring-boot:run
```

### Expected result

- application started on a dynamic port
- MySQL reachable through local configuration or compose
- OpenAPI documentation available to validate endpoints

## Configuration

The module uses optional .env import in application.properties:

- spring.config.import=optional:file:.env

Common variables in compose:

- MYSQL_DATABASE, MYSQL_USER, MYSQL_PASSWORD
- SPRING_AGENDAMENTO_LOCAL_PORT, SPRING_AGENDAMENTO_DOCKER_PORT

## Tests

```powershell
# module tests
mvn -B -f ms-agendamento/pom.xml test

# E2E with Testcontainers
mvn -B -f ms-agendamento/pom.xml -Ddocker.e2e=true -Dtest=AgendamentoFlowE2ETest test

# coverage
mvn -B -f ms-agendamento/pom.xml jacoco:report
```

## Docker

The project has docker-compose.yaml inside ms-agendamento with MySQL + app.

```powershell
Set-Location "c:\Users\leo_a\projetos\desafioMagalu\ms-agendamento"
docker compose up --build
```

## Troubleshooting

- Database connection errors: validate MYSQL_* and SPRING_APPLICATION_JSON in compose.
- E2E failures: make sure Docker is running before -Ddocker.e2e=true.
- Port conflicts: adjust port variables in .env.

## References

- roadmap and planning: [plano.md](plano.md)
- architecture diagrams: [docs/architecture](docs/architecture)
- challenge statement: [PROBLEM.md](PROBLEM.md)
