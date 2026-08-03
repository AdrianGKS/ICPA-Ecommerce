# 🛒 ICPA E-Commerce - API Backend

> **ICPA E-Commerce** é uma API RESTful robusta desenvolvida em **Spring Boot** para gerenciamento completo de uma plataforma de comércio eletrônico. A aplicação contempla autenticação segura via JWT, gerenciamento de usuários e endereços, catálogo de produtos, processamento de pedidos, envio de e-mails transacionais (boas-vindas e recuperação de senha) e integração com armazenamento em nuvem (AWS S3) para gerenciamento de arquivos.

---

## 📋 Sumário

* [Sobre o Projeto](https://www.google.com/search?q=%23-sobre-o-projeto)
* [✨ Principais Funcionalidades](https://www.google.com/search?q=%23-principais-funcionalidades)
* [🛠️ Tecnologias Utilizadas](https://www.google.com/search?q=%23%EF%B8%8F-tecnologias-utilizadas)
* [📁 Estrutura do Projeto](https://www.google.com/search?q=%23-estrutura-do-projeto)
* [🔌 Endpoints da API](https://www.google.com/search?q=%23-endpoints-da-api)
* [⚙️ Configuração e Instalação](https://www.google.com/search?q=%23%EF%B8%8F-configura%C3%A7%C3%A3o-e-instala%C3%A7%C3%A3o)
* [🔑 Autenticação e Segurança](https://www.google.com/search?q=%23-autentica%C3%A7%C3%A3o-e-seguran%C3%A7a)
* [📄 Documentação (Swagger)](https://www.google.com/search?q=%23-documenta%C3%A7%C3%A3o-swagger)
* [📝 Licença](https://www.google.com/search?q=%23-licen%C3%A7a)

---

## 🎯 Sobre o Projeto

O backend do **ICPA E-Commerce** foi projetado seguindo as melhores práticas de desenvolvimento de software em Java, aplicando os princípios do **Domain-Driven Design (DDD)**, padronização de respostas com **DTOs (Data Transfer Objects)** e tratamento centralizado de exceções.

O sistema atende a todos os fluxos críticos de uma loja virtual moderna:

* Cadastro e autenticação de clientes e administradores.
* Recuperação segura de senha com envio de token por e-mail.
* Gestão de produtos categorizados.
* Processamento e acompanhamento do status de pedidos.
* Upload e download seguro de arquivos/imagens integrados com **AWS S3**.

---

## ✨ Principais Funcionalidades

### 🔐 Autenticação e Usuários

* **Registro e Autenticação:** Criação de conta de usuário com perfis diferenciados (`ADMIN`, `USER`) e autenticação via JWT (*JSON Web Token*).
* **Gerenciamento de Perfil e Endereço:** Cadastro e atualização de dados pessoais e endereço de entrega.
* **Recuperação de Senha:** Solicitação de redefinição via e-mail com token temporário e validação segura.
* **E-mails Transacionais:** Envio de mensagem de boas-vindas após registro e e-mail com instruções de redefinição de senha.

### 🛍️ Catálogo de Produtos

* **Crud de Produtos:** Cadastro, listagem paginada, detalhamento e atualização de produtos.
* **Categorização:** Organização de produtos por categorias (`EnumProductCategory`).
* **Associação de Imagens:** Upload de arquivos de imagem vinculados aos produtos armazenados no S3.

### 📦 Gestão de Pedidos

* **Criação de Pedidos:** Registro de compras vinculadas ao usuário autenticado.
* **Formas de Pagamento e Status:** Controle de tipo de pagamento (`EnumPaymenType`) e fluxo de status do pedido (`EnumOrderStatus`).

### ☁️ Armazenamento em Nuvem (AWS S3)

* **Upload / Download de Arquivos:** Provedor de armazenamento em nuvem desacoplado (`CloudStorageProvider`) para upload seguro de mídias e documentos.
* **Validação de Arquivos:** Anotações e validadores customizados para validar extensão (`@AllowedFileExtensions`) e tipo MIME (`@AllowedContentTypes`).

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 17+
* **Framework Principal:** Spring Boot 3
* **Segurança:** Spring Security + JWT (JSON Web Token)
* **Persistência de Dados:** Spring Data JPA / Hibernate
* **Armazenamento em Nuvem:** AWS SDK for Java (Amazon S3 Integration)
* **Envio de E-mails:** Spring Boot Starter Mail
* **Documentação:** SpringDoc OpenAPI / Swagger UI
* **Gerenciador de Dependências:** Apache Maven

---

## 📁 Estrutura do Projeto

```text
backend/src/main/java/com/api/ICPAEcommerce/
├── controllers/                  # Controladores REST (Endpoints)
│   ├── AuthenticationController.java
│   ├── FileController.java
│   ├── OrderController.java
│   ├── ProductController.java
│   └── UserController.java
│
├── domain/                       # Entidades de Domínio, DTOs e Enums
│   ├── file/                     # Domínio de arquivos e validadores customizados
│   │   ├── validation/           # Anotações e validadores (@AllowedContentTypes, etc.)
│   │   ├── FileReference.java
│   │   └── ...
│   ├── order/                    # Domínio de pedidos (Order, Status, PaymentType)
│   ├── product/                  # Domínio de produtos e categorias
│   └── user/                     # Domínio de usuários, perfis, endereços e autenticação
│
├── infra/                        # Configurações de Infraestrutura
│   ├── exception/                # ExceptionsHandler (@ControllerAdvice)
│   ├── s3/                       # Provedores e configurações do Amazon S3
│   ├── security/                 # Filtros e configurações de segurança JWT
│   └── springdoc/                # Configuração do Swagger/OpenAPI
│
├── repositories/                 # Interfaces Spring Data JPA
│   ├── FileReferenceRepository.java
│   ├── OrderRepository.java
│   ├── ProductRepository.java
│   └── UserRepository.java
│
└── services/                     # Regras de Negócio e Serviços
    ├── OrderService.java
    ├── ProductService.java
    ├── RegisterEmailService.java
    ├── ResetPasswordService.java
    ├── StorageService.java
    └── UserService.java

```

---

## 🔌 Endpoints da API

### 🔑 Autenticação (`/auth`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/auth/login` | Realiza login e retorna o Token JWT | Público |
| `POST` | `/auth/recover-password` | Solicita e-mail de redefinição de senha | Público |
| `POST` | `/auth/reset-password` | Atualiza a senha utilizando o token recebido | Público |

### 👤 Usuários (`/users`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/users` | Cadastra um novo usuário no sistema | Público |
| `GET` | `/users/me` | Retorna dados do usuário autenticado | Autenticado |
| `PUT` | `/users/me` | Atualiza dados do usuário autenticado | Autenticado |

### 🛍️ Produtos (`/products`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `GET` | `/products` | Lista produtos cadastrados (com paginação) | Público |
| `GET` | `/products/{id}` | Busca detalhes de um produto por ID | Público |
| `POST` | `/products` | Cadastra um novo produto | Admin |
| `PUT` | `/products/{id}` | Atualiza informações de um produto | Admin |

### 📦 Pedidos (`/orders`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/orders` | Cria um novo pedido de compra | Autenticado |
| `GET` | `/orders` | Lista os pedidos do usuário autenticado | Autenticado |
| `GET` | `/orders/{id}` | Detalhes de um pedido específico | Autenticado |

### 📁 Arquivos (`/files`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/files/upload` | Envia um arquivo para o Amazon S3 | Autenticado |
| `GET` | `/files/download/{id}` | Gera URL/Stream de download de um arquivo | Autenticado |

---

## ⚙️ Configuração e Instalação

### 🛠️ Pré-requisitos

* **Java JDK 17** ou superior instalado.
* **Maven 3.8+** (ou utilizar o wrapper `./mvnw` incluso).
* **Banco de Dados Relacional** (PostgreSQL / MySQL / H2 conforme configurado).
* Conta no **AWS S3** (para upload de arquivos) ou credenciais de LocalStack para ambiente local.

### 1. Clonar o Repositório

```bash
git clone https://github.com/SeuUsuario/ICPA-Ecommerce.git
cd ICPA-Ecommerce/backend

```

### 2. Configurar Variáveis de Ambiente

Edite ou configure o arquivo `src/main/resources/application.properties` com suas credenciais do Banco de Dados, E-mail e AWS S3:

```properties
# Configuração do Banco de Dados
spring.datasource.url=jdbc:postgresql://localhost:5432/icpa_ecommerce
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# Spring Security - JWT
api.security.token.secret=${JWT_SECRET:sua_chave_secreta_jwt}

# Serviço de Mail (SMTP)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=seu_email@gmail.com
spring.mail.password=sua_senha_de_app

# AWS S3 Storage
storage.s3.bucket=seu-bucket-s3
storage.s3.region=us-east-1
storage.s3.access-key=SUA_AWS_ACCESS_KEY
storage.s3.secret-key=SUA_AWS_SECRET_KEY

```

### 3. Compilar e Executar a Aplicação

```bash
# Utilizando o Maven Wrapper
./mvnw clean package
./mvnw spring-boot:run

```

A aplicação iniciará por padrão em `http://localhost:8080`.

---

## 🔑 Autenticação e Segurança

A API utiliza **Spring Security** com autenticação **Stateless** baseada em **Bearer Tokens JWT**.
Para acessar endpoints protegidos:

1. Faça requisição `POST /auth/login` enviando credenciais válidas.
2. Copie o `token` retornado na resposta.
3. Nas requisições protegidas, adicione o cabeçalho HTTP:
```text
Authorization: Bearer <seu_token_jwt>

```



---

## 📄 Documentação (Swagger)

Com a aplicação rodando, acesse a interface interativa do Swagger para visualizar e testar todos os endpoints disponíveis:

* **Swagger UI:** `http://localhost:8080/swagger-ui.html`
* **OpenAPI Doc:** `http://localhost:8080/v3/api-docs`

---

## 📝 Licença

Este projeto é disponibilizado sob a licença [MIT](https://github.com/AdrianGKS/ICPA-Ecommerce/blob/main/LICENSE) - veja o arquivo de licença para mais detalhes.
