![Java CI with Maven](https://github.com/horion/api_challenge/workflows/Java%20CI%20with%20Maven/badge.svg)

# Desafio Challenge API Dextra


## Tecnologias utilizadas

Spring Boot  
Java 11  
Junit5  
MongoDB  
Swagger  
Embedded MongoDB (Mock do Banco de dados)  
Spring Data MongoDB  
Docker  
DockerHub (Armazenar imagem buildada)  
GitHub Actions (CI/CD)  
Docker-compose  
Maven  

## Decisões Arquiteturais  

Spring Boot: Utilizado para servir como serviço Rest e Injeção de Dependência;

Docker: Facilitar a construção do projeto;  

MongoDB: Banco de dados NOSQL orientado a documento, muito útil em manipulação de JSON;  

Testes unitários: O foco dos testes ficou no Core da aplicação, evitando testes de get e set em modelos, só para aumentar a cobertura de código;  

Generalização: Criei classes de serviço e de persistência, todas baseadas em interfaces, para garantir a manutenabilidade, para trocar a implementação, basta criar uma nova classe que implementa a interface e especificar na injeção de dependência;  

Docker-compose: Facilitar o deploy e configuração da aplicação.  

Embedded MongoDB: Realizar testes de integração com um banco de dados igual ao utilizado pela aplicação  

Swagger: Documentação simples e intuitiva  

Java: Afinidade técnica  

GitHub Actions: Integração nativa com o código fonte do GitHub  

DockerHub: Registry público robusto,confiável e integrado ao GitHub Actions    


## Estrutura do projeto

Utilizei a estrutura de projeto baseado na responsabilidade dos níveis, ou seja, controllers ficaram no package controllers, services no package services e etc.  
Outro detalhe sobre o projeto, é a ideia do padrão DTO  e Form. DTO só transporte e apenas com lógicas de conversão e o Form, com lógicas de conversão e validação.  
Segui o princípio Open Close nos services, ou seja, a ideia é sempre extender  
Segui o padrão Service-Repository, ou seja, os controllers não acessam direto o service, diminuindo o acoplamento  
```
├── api.iml
├── Dockerfile
├── HELP.md
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── br
    │   │       └── com
    │   │           └── challenge
    │   │               └── dextra
    │   │                   └── api
    │   │                       ├── ApiApplication.java
    │   │                       ├── config
    │   │                       │   ├── doc
    │   │                       │   │   └── SwaggerConfig.java
    │   │                       │   └── validation
    │   │                       │       └── ErrorHandlingValidation.java
    │   │                       ├── controller
    │   │                       │   ├── CharacterController.java
    │   │                       │   ├── dto
    │   │                       │   │   ├── CharacterDTO.java
    │   │                       │   │   ├── ErrorFormDTO.java
    │   │                       │   │   └── Response.java
    │   │                       │   ├── exception
    │   │                       │   │   └── HouseNotFoundException.java
    │   │                       │   └── form
    │   │                       │       ├── CharacterForm.java
    │   │                       │       └── Form.java
    │   │                       ├── model
    │   │                       │   └── Character.java
    │   │                       ├── properties
    │   │                       │   └── Properties.java
    │   │                       ├── repository
    │   │                       │   └── CharacterRepository.java
    │   │                       ├── service
    │   │                       │   ├── CharacterService.java
    │   │                       │   └── impl
    │   │                       │       └── CharacterServiceImpl.java
    │   │                       └── util
    │   │                           └── Util.java
    │   └── resources
    │       ├── application.properties
    │       ├── static
    │       └── templates
    └── test
        ├── java
        │   └── br
        │       └── com
        │           └── challenge
        │               └── dextra
        │                   └── api
        │                       ├── ApiApplicationTests.java
        │                       ├── controller
        │                       │   └── CharacterControllerTest.java
        │                       ├── repository
        │                       │   └── CharacterRepositoryTest.java
        │                       ├── service
        │                       │   └── impl
        │                       │       └── CharacterServiceImplTest.java
        │                       └── util
        │                           └── UtilTest.java
        └── resources
            └── application.properties
```  

## Deploy

### Requisitos: Linux, Docker e Docker-compose

Seguir o tutorial de instalação do [docker](https://docs.docker.com/v17.09/engine/installation/#updates-and-patches)  

Seguir o tutorial de instalação do [docker-compose](https://docs.docker.com/compose/install/)  

Baixar o [docker-compose.yaml](https://github.com/horion/api_challenge/blob/master/docker-compose.yaml)     

Executar os comandos:  

```
docker-compose pull
docker-compose up -d
```

## Swagger Doc

Para acessar o Swagger Doc: http://localhost/swagger-ui.html  


## Utilização da API

Os métodos que fazem requisição ao Potter Api, necessitam do token da Potter Api. Esse token é obrigatório.
Caso não queira cadastrar, utilizar o token abaixo.  

```
token: $2a$10$ZOElEX6GhOLFACcXBcrSKuZXzBs0GJOpg/0/NO6P31tz97ntOQOhS  
```

Exemplo de criação de usuário:  

```
curl -X POST "http://localhost/api/characters" -H "accept: */*" -H "token: $2a$10$ZOElEX6GhOLFACcXBcrSKuZXzBs0GJOpg/0/NO6P31tz97ntOQOhS" -H "Content-Type: application/json" -d "{ \"name\": \"Harry Potter\", \"role\": \"student\", \"school\": \"Hogwarts School of Witchcraft and Wizardry\", \"house\": \"5a05e2b252f721a3cf2ea33f\", \"patronus\": \"stag\"}"
```

O único campo não obrigatorio na criação/atualização do usuário é o Patronus, segue explicação dos fãs:
[History Patronus](https://aminoapps.com/c/potter-amino-em-portugues/page/blog/bruxos-das-trevas-nao-podem-conjurar-um-patrono/j4XN_0eSKuJDZBzDljKjEeEKbVpZ1rmgnB)  



OBS: Para testes podemos usar diretamente o Swagger ou Postman  

## Ambiente no GCP  

Para testes, fiz um deploy da aplicação no Google Cloud Plataform: [Ambiente no GCP](http://34.95.241.44/swagger-ui.html)  


## Funcionamento do CI/CD  

A cada commit na branch master, é disparado um gatilho, com isso rodamos a CI, que realiza o build do projeto com todos os testes implementados, se falhar a CI encerra o runner e envia um e-mail relatando o problema. Se passar começa a fazer os passoa do CD, que consiste em buildar uma imagem Docker e subir na minha conta do DockerHub.   

[Conta DockerHub](https://hub.docker.com/repository/docker/horion2/api)  

[GitHub Action](https://github.com/horion/api_challenge/actions)  

[Configuração do Runner](https://github.com/horion/api_challenge/blob/master/.github/workflows/maven.yml)  
