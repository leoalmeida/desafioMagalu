# desafioMagalu

[Português](README.md) | [English](README.en.md)

Serviço de agendamento de comunicações construído com Spring Boot, JPA, MySQL, OpenAPI e Docker Compose.

## Resumo Executivo

Este repositório implementa o desafio backend da Magalu com foco no serviço `ms-agendamento`, responsável por registrar solicitações de envio de comunicação e disponibilizar APIs para consulta e remoção do agendamento.

Objetivos do projeto:

- receber solicitações de agendamento por API REST
- persistir os dados de forma preparada para evolução do ciclo de entrega
- expor contratos claros e documentação de API
- garantir qualidade com testes, cobertura e pipeline automatizado

Escopo principal:

- `ms-agendamento`: cadastro, consulta, listagem e remoção de agendamentos
- `docs/architecture`: diagramas e apoio arquitetural
- `.github/workflows`: automação de build, testes e cobertura

Arquitetura resumida:

Cliente
	|
API REST `ms-agendamento`
	|
Camadas de domínio e persistência
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

## Status do Projeto

- serviço principal concentrado em `ms-agendamento`
- pipelines de build, testes e cobertura publicados
- documentação arquitetural disponível em `docs/architecture`
- roadmap detalhado em [plano.md](plano.md)

## Build, Testes e Cobertura

| Tipo | Status |
| --- | --- |
| Build | [![Build ms-agendamento](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml/badge.svg?branch=main)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Testes | [![Testes ms-agendamento](https://img.shields.io/github/actions/workflow/status/leoalmeida/desafioMagalu/workflow.yml?branch=main&label=testes-ms-agendamento)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |
| Cobertura | [![Codecov](https://codecov.io/gh/leoalmeida/desafioMagalu/branch/main/graph/badge.svg?flag=ms-agendamento)](https://codecov.io/gh/leoalmeida/desafioMagalu) [![JaCoCo](https://img.shields.io/badge/cobertura-JaCoCo-blue)](https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml) |

Relatorios JaCoCo sao gerados no CI e tambem podem ser executados localmente em ms-agendamento/target/site/jacoco/.

## Sumario

- [Resumo Executivo](#resumo-executivo)
- [Stack](#stack)
- [Status do Projeto](#status-do-projeto)
- [Build, Testes e Cobertura](#build-testes-e-cobertura)
- [Modulos do Repositorio](#modulos-do-repositorio)
- [Portas e Integracoes](#portas-e-integracoes)
- [Requisitos](#requisitos)
- [Como Rodar Rapido](#como-rodar-rapido)
- [Configuracao](#configuracao)
- [Testes](#testes)
- [Docker](#docker)
- [Troubleshooting](#troubleshooting)
- [Referencias](#referencias)

## Modulos do Repositorio

Funcionalidades principais:

- agendar envio de comunicacao com data/hora, destinatario, mensagem, tipo e status
- consultar um agendamento por ID
- listar agendamentos
- remover agendamento por ID

Estrutura principal:

- `ms-agendamento`
- `docs/architecture`
- `.github/workflows`

## Portas e Integracoes

- ms-agendamento: server.port=0 (porta dinamica)
- banco MySQL (docker-compose): 3306 no container, mapeamento pela variavel MYSQL_LOCAL_PORT

## Requisitos

- JDK 17+
- Maven 3.9+
- Docker (para ambiente completo e E2E)

## Como Rodar Rapido

### Subida local essencial

1. executar o build do modulo
2. subir o MySQL via Docker Compose ou apontar para uma instancia existente
3. iniciar o `ms-agendamento`
4. validar os endpoints e a documentacao OpenAPI

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

### Resultado esperado

- aplicacao iniciada com porta dinamica
- banco MySQL acessivel via configuracao local ou compose
- documentacao OpenAPI disponivel para validacao dos endpoints

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

## Referencias

- roadmap e planejamento: [plano.md](plano.md)
- backlog executivo do portfólio: [../votacao-backend/docs/portfolio/backlog-executivo.md](../votacao-backend/docs/portfolio/backlog-executivo.md)
- matriz comparativa dos desafios: [../votacao-backend/docs/portfolio/matriz-comparativa.md](../votacao-backend/docs/portfolio/matriz-comparativa.md)
- diagramas: [docs/architecture](docs/architecture)
- enunciado do desafio: [PROBLEM.md](PROBLEM.md)
