# desafioMagalu

[Português](README.md) | [English](README.en.md)

## Build, Testes e Cobertura

| Tipo | Status |
| --- | --- |
| Build | [![Build ms-agendamento](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml/badge.svg?branch=main)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Testes | [![Testes ms-agendamento](https://img.shields.io/github/actions/workflow/status/leoalmeida/desafioMagalu/workflow.yml?branch=main&label=testes-ms-agendamento)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Cobertura | [![Codecov](https://codecov.io/gh/leoalmeida/desafioMagalu/branch/main/graph/badge.svg?flag=ms-agendamento)](https://codecov.io/gh/leoalmeida/desafioMagalu) [![JaCoCo](https://img.shields.io/badge/cobertura-JaCoCo-blue)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |

Relatorios JaCoCo sao gerados no CI e tambem podem ser executados localmente em ms-agendamento/target/site/jacoco/.

![Java](https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.3-6DB33F?logo=springboot&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)
![Testcontainers](https://img.shields.io/badge/Testcontainers-1.20.6-2496ED?logo=docker&logoColor=white)

Projeto do desafio backend da Magalu com foco no servico ms-agendamento.

## Sumario

- [Visao Geral](#visao-geral)
- [Build, Testes e Cobertura](#build-testes-e-cobertura)
- [Portas e Discovery](#portas-e-discovery)
- [Requisitos](#requisitos)
- [Como Rodar](#como-rodar)
- [Configuracao](#configuracao)
- [Testes](#testes)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)

## Visao Geral

Funcionalidades principais:

- Agendar envio de comunicacao com data/hora, destinatario, mensagem, tipo e status de entrega
- Consultar um agendamento por ID
- Listar todos os agendamentos
- Remover agendamento por ID

Estrutura principal:

- ms-agendamento
- docs/architecture
- .github/workflows

## Portas e Discovery

- ms-agendamento: server.port=0 (porta dinamica)
- banco MySQL (docker-compose): 3306 no container, mapeamento pela variavel MYSQL_LOCAL_PORT

## Requisitos

- JDK 17+
- Maven 3.9+
- Docker (para ambiente completo e E2E)

## Como Rodar

### 1. Build completo

```powershell
Set-Location "c:\Users\leo_a\projetos\desafioMagalu"
mvn -B -f ms-agendamento/pom.xml clean package
```

### 2. Executar aplicacao

```powershell
Set-Location "c:\Users\leo_a\projetos\desafioMagalu\ms-agendamento"
.\mvnw.cmd spring-boot:run
```

## Configuracao

O modulo utiliza import opcional de .env em application.properties:

- spring.config.import=optional:file:.env

Variaveis comuns no compose:

- MYSQL_DATABASE, MYSQL_USER, MYSQL_PASSWORD
- SPRING_AGENDAMENTO_LOCAL_PORT, SPRING_AGENDAMENTO_DOCKER_PORT

## Testes

```powershell
# testes do modulo
mvn -B -f ms-agendamento/pom.xml test

# E2E com Testcontainers
mvn -B -f ms-agendamento/pom.xml -Ddocker.e2e=true -Dtest=AgendamentoFlowE2ETest test

# cobertura
mvn -B -f ms-agendamento/pom.xml jacoco:report
```

## Docker

O projeto possui docker-compose.yaml em ms-agendamento com MySQL + aplicacao.

```powershell
Set-Location "c:\Users\leo_a\projetos\desafioMagalu\ms-agendamento"
docker compose up --build
```

## Troubleshooting

- Erro de conexao com banco: valide MYSQL_* e SPRING_APPLICATION_JSON no compose.
- Falha em E2E: confirme Docker ativo antes de rodar -Ddocker.e2e=true.
- Porta em conflito: ajuste variaveis de porta no .env.
