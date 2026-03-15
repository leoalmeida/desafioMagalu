<p align="center" width="100%">
    <img width="50%" src="https://github.com/buildrun-tech/buildrun-desafio-backend-magalu/blob/main/images/logo-magalu.png"> 
</p>


<h3 align="center">
  Desafio Backend da Magalu - ms-agendamento
</h3>

<p align="center">

  <img alt="License: MIT" src="https://img.shields.io/badge/license-MIT-%2304D361">
  <img alt="Java 17" src="https://img.shields.io/badge/Java-17-007396?logo=openjdk&logoColor=white">
  <img alt="Spring Boot 4" src="https://img.shields.io/badge/Spring%20Boot-4.0.3-6DB33F?logo=springboot&logoColor=white">
  <img alt="Maven" src="https://img.shields.io/badge/Maven-3.9+-C71A36?logo=apachemaven&logoColor=white">
  <img alt="MySQL" src="https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white">
  <img alt="Testcontainers" src="https://img.shields.io/badge/Testcontainers-1.20.6-2496ED?logo=docker&logoColor=white">
  <img alt="JaCoCo" src="https://img.shields.io/badge/Coverage-JaCoCo-brightgreen">
  <img alt="CI" src="https://img.shields.io/badge/CI-GitHub%20Actions-2088FF?logo=githubactions&logoColor=white">

</p>

<p align="center">
  <a href="https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml">
    <img alt="CI Workflow" src="https://github.com/leoalmeida/desafioMagalu/actions/workflows/workflow.yml/badge.svg?branch=main">
  </a>
  <a href="https://codecov.io/gh/leoalmeida/desafioMagalu">
    <img alt="Codecov" src="https://codecov.io/gh/leoalmeida/desafioMagalu/branch/main/graph/badge.svg?flag=ms-agendamento">
  </a>
</p>

## Sobre

Este repositório contém a implementação do desafio de backend da Magalu, com foco no serviço `ms-agendamento`.

Funcionalidades principais:

- Agendar envio de comunicação com data/hora, destinatário, mensagem, tipo e status de entrega.
- Consultar um agendamento por ID.
- Listar todos os agendamentos.
- Remover agendamento por ID.

## Status de Build, Testes e Cobertura

Status atual do módulo `ms-agendamento`:

- Build local Maven: OK
- Testes unitários/integrados: OK
- E2E com Docker/Testcontainers: configurado via flag `-Ddocker.e2e=true`
- Cobertura: relatório JaCoCo gerado em `ms-agendamento/target/site/jacoco/`

Observação: os badges dinâmicos acima estão configurados para o repositório `leoalmeida/desafioMagalu`.

## Workflows Relevantes

- Pipeline principal (lint, testes, cobertura e build): [.github/workflows/workflow.yml](.github/workflows/workflow.yml)
- Build e push de imagens Docker: [.github/workflows/docker.yml](.github/workflows/docker.yml)
- Segurança com OWASP ZAP: [.github/workflows/zapscan.yml](.github/workflows/zapscan.yml)

## Como Rodar Localmente

Pré-requisitos:

- Java 17+
- Maven 3.9+
- Docker (para E2E)

Comandos úteis:

```bash
# Executar build e testes
mvn -B -f ms-agendamento/pom.xml clean package

# Executar somente testes
mvn -B -f ms-agendamento/pom.xml test

# Executar E2E com Testcontainers
mvn -B -f ms-agendamento/pom.xml -Ddocker.e2e=true -Dtest=AgendamentoFlowE2ETest test

# Gerar relatório de cobertura
mvn -B -f ms-agendamento/pom.xml jacoco:report
```

## Estrutura

- Serviço principal: [ms-agendamento](ms-agendamento)
- Enunciado do desafio: [PROBLEM.md](PROBLEM.md)
