# Plano de Trabalho - desafioMagalu

## 1. Objetivo

Entregar e evoluir o serviço de agendamento de comunicações, garantindo:

- cadastro confiável de solicitações de envio
- consulta e remoção de agendamentos por API REST
- persistência consistente para suportar evolução futura do motor de envio
- qualidade contínua com testes automatizados, documentação e pipeline

## 2. Escopo Atual do Repositório

Módulos e diretórios principais:

- `ms-agendamento`
- `docs/architecture`
- `.github/workflows`

Tecnologias base:

- Java 17
- Spring Boot 4.0.3
- Spring Data JPA
- MySQL
- Flyway
- Springdoc OpenAPI
- Docker Compose
- GitHub Actions + Codecov

## 3. Arquitetura e Decisões

Fluxo macro:

Cliente
   |
API REST `ms-agendamento`
   |
Camadas de dominio e persistencia
   |
MySQL

Documentos de apoio:

- [Context Diagram](docs/architecture/context-diagram.puml)
- [Container Diagram](docs/architecture/container-diagram.puml)
- [Component Diagram](docs/architecture/component-diagram-backend.puml)

Decisões obrigatórias para novas features:

- contrato OpenAPI primeiro
- modelo de dados preparado para ciclo completo do agendamento e atualizacao de status
- evolucao do dominio sem acoplar desde ja o canal real de envio
- migrations versionadas com Flyway

## 4. Estratégia de Desenvolvimento

Modelo de execução: vertical slices por capacidade de negócio.

Cada slice deve incluir:

1. modelagem de domínio
2. persistência (`repository`)
3. regra de negócio (`service`)
4. API (`controller` + DTOs + validações)
5. documentação OpenAPI
6. testes unitários
7. testes de integração

Estrutura padrão do serviço:

- `controller`
- `service`
- `repository`
- `domain`
- `dto`
- `config`

## 5. Fases de Entrega

### Fase 1 - Foundation

Objetivo: estabilizar ambiente e padrões de engenharia.

Entregáveis:

- ambiente local com MySQL via Docker Compose
- variáveis de ambiente e configuração revisadas
- build, checkstyle, cobertura e documentação de execução local padronizados
- migrations iniciais e convenções de estrutura do módulo definidas

Critérios de aceite:

- build do módulo com `clean package`
- aplicação sobe localmente e conecta ao banco sem intervenção manual adicional

### Fase 2 - Domínio de Agendamento

Objetivo: consolidar os casos de uso centrais do desafio.

Entregáveis mínimos:

- endpoint para criar agendamento com data/hora, destinatário, mensagem, tipo e status
- endpoint para consultar agendamento por identificador
- endpoint para listar agendamentos
- endpoint para remover agendamento

Critérios de aceite:

- regras de validação documentadas e cobertas por testes
- persistência pronta para futura atualização de status de entrega

### Fase 3 - Contrato, Integração e Robustez

Objetivo: garantir estabilidade de API e confiabilidade operacional.

Entregáveis:

- documentação OpenAPI e exemplos de requisição/resposta
- testes de integração com MySQL real via Testcontainers
- testes de contrato e regressão de API
- tratamento consistente de erros e respostas padronizadas

Critérios de aceite:

- contrato OpenAPI aderente à implementação
- cenários críticos de persistência e validação testados em integração

### Fase 4 - Observabilidade e Release Readiness

Objetivo: elevar a prontidão do serviço para avaliação e operação.

Entregáveis:

- logs e health checks padronizados
- cobertura mínima acordada e relatório JaCoCo consistente
- troubleshooting e instruções de operação revisados
- revisão de performance, tratamento de exceções e configuração sensível

Critérios de aceite:

- pipeline verde
- documentação suficiente para executar, testar e avaliar o serviço

## 6. Estratégia de Testes

### 6.1 Unitários

Ferramentas:

- JUnit 5
- Mockito

Cobertura mínima por slice:

- regras de negócio
- cenários de erro
- validações e mapeamentos

### 6.2 Integração

Ferramentas:

- Spring Boot Test
- Testcontainers (MySQL)
- H2 apenas para cenários de teste leves quando apropriado

Escopo:

- integração repositório + banco
- integração entre camadas da API
- compatibilidade de migrations

### 6.3 Contrato de API

Ferramentas sugeridas:

- validação OpenAPI no pipeline
- testes de contrato com MockMvc ou Rest Assured

Objetivos:

- garantir aderência ao contrato OpenAPI
- prevenir regressões nos endpoints públicos

## 7. CI/CD e Releases

Pipeline mínimo:

1. build
2. testes unitários
3. testes de integração
4. cobertura e publicação de relatório
5. build da imagem Docker

Ambientes-alvo:

- local
- dev
- homologação
- prod

Política de merge:

- pipeline verde obrigatório
- cobertura mínima respeitada
- documentação impactada atualizada

## 8. Roadmap Sugerido (4 Sprints)

Premissa de conversão para planejamento inicial:

- 2 pontos = 1 dia-pessoa
- a conversão serve para previsão macro, não para compromisso fechado

### Sprint 1

Estimativa: 16 a 24 pontos

Equivalente: 8 a 12 dias-pessoa

- foundation: revisar `docker-compose`, `.env`, migrations e boot local do serviço
- foundation: padronizar build Maven, cobertura, checkstyle e execução de testes
- `ms-agendamento`: revisar modelo inicial de domínio, DTOs e contrato da API
- docs: consolidar arquitetura e guias de execução local

### Sprint 2

Estimativa: 20 a 28 pontos

Equivalente: 10 a 14 dias-pessoa

- `ms-agendamento`: concluir criação, consulta, listagem e remoção de agendamentos
- `ms-agendamento`: consolidar validações de entrada e regras de negócio
- testes: cobrir cenários principais com testes unitários e integração
- docs: publicar OpenAPI atualizado para os endpoints estabilizados

### Sprint 3

Estimativa: 16 a 24 pontos

Equivalente: 8 a 12 dias-pessoa

- qualidade: reforçar padronização de erros e respostas da API
- persistência: validar schema para evolução futura de status de entrega
- testes: ampliar cenários com Testcontainers e regressão de API
- docs: registrar exemplos de uso, payloads e limitações conhecidas

### Sprint 4

Estimativa: 12 a 20 pontos

Equivalente: 6 a 10 dias-pessoa

- observabilidade: revisar logs, actuator e saúde da aplicação
- hardening: revisar exceções, timeouts e configuração sensível
- release: validar imagem Docker, pipeline e prontidão para avaliação final
- docs: consolidar troubleshooting, operação e checklist de entrega

## 9. Backlog por Serviço

Padrao de identificacao das historias:

- `PROJETO-SERVICO-NNN`
- `PROJETO`: `VTB`, `MAG` ou `BTG`
- `SERVICO`: codigo curto do modulo ou contexto
- `NNN`: sequencial de tres digitos

### `ms-agendamento`

Historia `MAG-AGD-001`

- como consumidor da API, quero criar um agendamento de comunicacao com os dados obrigatorios para registrar a solicitacao de envio

Criterios de aceite:

- o endpoint aceita data/hora, destinatario, mensagem, tipo e status inicial
- validacoes de entrada rejeitam payloads invalidos com erro consistente
- o registro fica persistido em banco apos a criacao

Historia `MAG-AGD-002`

- como operador, quero consultar um agendamento por identificador para acompanhar seu estado

Criterios de aceite:

- a API retorna os dados completos do agendamento quando existente
- a API retorna erro apropriado quando o identificador nao existe
- testes cobrem sucesso e nao encontrado

Historia `MAG-AGD-003`

- como operador, quero listar agendamentos para obter visao operacional do volume cadastrado

Criterios de aceite:

- a listagem retorna os registros persistidos de forma consistente
- o contrato de resposta esta documentado no OpenAPI

Historia `MAG-AGD-004`

- como operador, quero remover um agendamento para excluir registros indevidos antes do envio futuro

Criterios de aceite:

- a remocao remove ou invalida o registro conforme a estrategia definida
- o comportamento da API para exclusao de identificador inexistente e documentado e testado

Historia `MAG-AGD-005`

- como time tecnico, quero manter o schema e o contrato preparados para futura evolucao do status de entrega

Criterios de aceite:

- o modelo de dados comporta evolucao de estados sem retrabalho estrutural relevante
- migrations e documentacao deixam clara a intencao de extensibilidade

## 10. RACI Simplificada

| Atividade | Backend | Frontend | QA | DevOps |
| --- | --- | --- | --- | --- |
| Dominio, persistencia e API de agendamento | A/R | I | C | I |
| Contrato OpenAPI e exemplos de uso | A/R | I | C | I |
| Estrategia e execucao de testes | R | I | A | C |
| Docker, banco local e pipeline | C | I | C | A/R |
| Troubleshooting e readiness de entrega | R | I | C | A/R |

## 11. Priorização MoSCoW

### Must Have

- criação, consulta, listagem e remoção de agendamentos
- persistência em MySQL com migrations versionadas
- documentação OpenAPI atualizada
- testes unitários e de integração para casos críticos
- pipeline de CI com build, testes e cobertura

### Should Have

- payload de domínio preparado para futura atualização de status de entrega
- respostas de erro padronizadas
- health checks e troubleshooting bem documentados

### Could Have

- cenários adicionais de resiliência e performance
- geração de exemplos automatizados para documentação
- métricas operacionais mais detalhadas

### Won't Have (nesta fase)

- implementação real do envio por email, SMS, push ou WhatsApp
- orquestração avançada em múltiplos serviços
- autenticação/autorização completa com IAM externo

## 12. Estimativa por Frente

Referência inicial para planejamento macro:

- foundation e setup de ambiente: 10 a 16 pontos = 5 a 8 dias-pessoa
- domínio de agendamento: 16 a 24 pontos = 8 a 12 dias-pessoa
- documentação e contrato de API: 8 a 12 pontos = 4 a 6 dias-pessoa
- qualidade e testes: 10 a 16 pontos = 5 a 8 dias-pessoa
- observabilidade e hardening: 6 a 10 pontos = 3 a 5 dias-pessoa

Observações:

- as estimativas consideram complexidade técnica moderada e equipe familiarizada com Spring Boot e MySQL
- a reestimativa deve acontecer ao final de cada sprint com base em throughput real

## 13. Definition of Done

Antes de cada merge:

- testes unitários passando
- testes de integração passando
- contrato OpenAPI atualizado e válido
- cobertura dentro do mínimo acordado
- build Docker funcionando
- pipeline CI verde
- documentação impactada atualizada

## 14. Riscos e Mitigações

Riscos principais:

- modelo de dados insuficiente para suportar evolução do status de entrega
- divergência entre contrato e implementação da API
- instabilidade de ambiente local por configuração de banco

Mitigações:

- revisão antecipada do schema com foco em extensibilidade
- validação de contrato em CI
- padronização de `.env`, Docker Compose e documentação operacional