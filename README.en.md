# desafioMagalu

[Português](README.md) | [English](README.en.md)

## Build, Tests and Coverage

| Type | Status |
| --- | --- |
| Build | [![Build ms-agendamento](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml/badge.svg?branch=main)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Tests | [![Tests ms-agendamento](https://img.shields.io/github/actions/workflow/status/leoalmeida/desafioMagalu/workflow.yml?branch=main&label=tests-ms-agendamento)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Coverage | [![Codecov](https://codecov.io/gh/leoalmeida/desafioMagalu/branch/main/graph/badge.svg?flag=ms-agendamento)](https://codecov.io/gh/leoalmeida/desafioMagalu) [![JaCoCo](https://img.shields.io/badge/coverage-JaCoCo-blue)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |

JaCoCo reports are generated in CI and can also be produced locally at ms-agendamento/target/site/jacoco/.

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-6DB33F?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-1.20.6-2496ED?logo=docker&logoColor=white)

Backend challenge project from Magalu focused on the ms-agendamento service.

## Table of Contents

- [Overview](#overview)
- [Build, Tests and Coverage](#build-tests-and-coverage)
- [Ports and Discovery](#ports-and-discovery)
- [Requirements](#requirements)
- [How to Run](#how-to-run)
- [Configuration](#configuration)
- [Tests](#tests)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)

## Overview

Main features:

- Schedule communication delivery with date/time, recipient, message, delivery type and status
- Get a schedule by ID
- List all schedules
- Delete a schedule by ID

Main structure:

- ms-agendamento
- docs/architecture
- .github/workflows

## Ports and Discovery

- ms-agendamento: server.port=0 (dynamic port)
- MySQL (docker-compose): 3306 in container, mapped through MYSQL_LOCAL_PORT

## Requirements

- JDK 17+
- Maven 3.9+
- Docker (for full environment and E2E)

## How to Run

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
