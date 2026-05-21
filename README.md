# Car Search App

Projeto desenvolvido para a sprint de **Arquitetura Orientada a Serviços e Web Services**, utilizando **Spring Boot**, **React Native**, **MySQL** e integração com a **Gemini API**.

A aplicação permite consultar, cadastrar e pesquisar veículos através de uma API RESTful integrada a um aplicativo mobile desenvolvido em React Native.

---

# Arquitetura da Aplicação

## Diagrama da Solução

<img width="1536" height="1024" alt="ChatGPT Image 15 de mai  de 2026, 07_29_03" src="https://github.com/user-attachments/assets/84777667-24a1-4041-b348-4cc62d1ec32f" />

---

# Tecnologias Utilizadas

## 🔹 Backend
- Java 21
- Spring Boot
- Spring Web
- Spring Data JPA
- MySQL
- Maven
- Swagger / OpenAPI
- Flyway

## 🔹 APIs Externas
- Gemini API (Google AI)

---

# Arquitetura do Projeto

O projeto foi desenvolvido utilizando arquitetura em camadas (SOA), promovendo organização, reutilização e separação de responsabilidades.

```text
Controller → Service → Repository → Banco de Dados
```

## Estrutura do Backend

```text
src
 ┣ controller
 ┣ service
 ┣ repository
 ┣ dto
 ┣ model
 ┣ exception
 ┗ config
```

---

# Endpoints da API

## Buscar todos os veículos

```http
GET /veiculos
```

### Resposta

```json
[
  {
    "id": 1,
    "marca": "Ford",
    "modelo": "Mustang",
    "versao": "GT"
  }
]
```

---

## Buscar veículo por parâmetros

```http
GET /veiculos/buscar?marca=Ford&modelo=Mustang
```

---

## Cadastrar veículo

```http
POST /veiculos
```

### Body

```json
{
  "marca": "Ford",
  "modelo": "Mustang",
  "versao": "GT"
}
```

---

## Consultar veículo com IA

Endpoint responsável por enviar um prompt para a Gemini API.

```http
POST /veiculos/consultar
```

### Body

```json
{
  "marca": "Ford",
  "modelo": "Mustang",
  "versao": "GT",
  "prompt": "Qual a potência desse veículo?"
}
```

---

# Integração com Gemini API

A aplicação realiza integração com a API Gemini para gerar respostas inteligentes baseadas nos dados enviados pelo usuário.

Fluxo:

```text
React Native → Spring Boot API → Gemini API
```

---

# Banco de Dados

O projeto utiliza MySQL com Spring Data JPA.

## Configuração

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/carsearch
spring.datasource.username=root
spring.datasource.password=senha
```

---

# Controle de Migrações

O projeto utiliza Flyway para controle de versões do banco de dados.

## Estrutura

```text
src/main/resources/db/migration
```

## Exemplo

```text
V1__create_table_veiculo.sql
```

---

# Swagger / OpenAPI

A documentação da API pode ser acessada através do Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

---

# Tratamento de Exceções

A aplicação possui tratamento global de exceções utilizando:

```text
GlobalExceptionHandler
```

Exemplo de resposta:

```json
{
  "erro": "Veículo não encontrado",
  "status": 404
}
```

---

# Como Executar o Projeto

## Backend

### 1. Clonar repositório

```bash
git clone https://github.com/PietroVP0777/challenge2026-ford-SOA.git
```

### 2. Entrar na pasta

```bash
cd backend
```

### 3. Executar aplicação

```bash
./mvnw spring-boot:run
```

---

# Padrões e Boas Práticas Aplicadas

 API RESTful  
 Arquitetura SOA  
 Separação em camadas  
 Uso correto de métodos HTTP  
 DTOs para transferência de dados  
 Tratamento global de exceções  
 Integração com serviço externo  
 Persistência com JPA  
 Controle de migrações com Flyway  
 Documentação com Swagger  

---

# Integrantes do Grupo

| Nome | RM |
|------|------|
| Pietro Vitor Pezzente | RM557283 |
| Eric Darakjian | RM557082 |
| Luciano Henrique Meriato Júnior | RM554546 |
| Kauã Soares Guimarães | RM559044 |
| Enzo Mikael Sanches | RM558887 |
