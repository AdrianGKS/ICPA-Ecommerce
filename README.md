# 🛒 ICPA E-Commerce - API Backend

> **ICPA E-Commerce** é uma API RESTful desenvolvida em **Spring Boot** para gerenciamento de uma plataforma de comércio eletrônico. A aplicação contempla autenticação via JWT, gerenciamento de usuários, catálogo de produtos, pedidos, e-mails transacionais e armazenamento de arquivos compatível com S3.

---

## 📋 Sumário

* [Sobre o Projeto](#-sobre-o-projeto)
* [✨ Principais Funcionalidades](#-principais-funcionalidades)
* [🛠️ Tecnologias Utilizadas](#️-tecnologias-utilizadas)
* [📁 Estrutura do Projeto](#-estrutura-do-projeto)
* [🔌 Endpoints da API](#-endpoints-da-api)
* [⚙️ Configuração e Instalação](#️-configuração-e-instalação)
* [🔑 Autenticação e Segurança](#-autenticação-e-segurança)
* [📄 Documentação (Swagger)](#-documentação-swagger)
* [📝 Licença](#-licença)

---

## 🎯 Sobre o Projeto

O backend do **ICPA E-Commerce** aplica separação por responsabilidades, uso de entidades de domínio, DTOs (*Data Transfer Objects*), serviços para as regras de negócio e tratamento centralizado de exceções.

O repositório contém atualmente o módulo `backend/`. Não há um módulo de frontend neste projeto.

---

## ✨ Principais Funcionalidades

### 🔐 Autenticação e Usuários

* **Registro e autenticação:** criação de usuários com perfis `ADMIN` e `USER`, utilizando JWT.
* **Gerenciamento de usuários:** cadastro, consulta, atualização e exclusão de usuários.
* **Recuperação de senha:** solicitação e alteração de senha por token temporário enviado por e-mail.
* **Endereços:** associação de endereço aos dados do usuário.
* **E-mails transacionais:** mensagens de cadastro e recuperação de senha.

### 🛍️ Catálogo de Produtos

* Cadastro, listagem, atualização e exclusão de produtos.
* Consulta por nome, categoria e código.
* Consulta do valor total em estoque.
* Categorização por `EnumProductCategory`.

### 📦 Gestão de Pedidos

* Criação e consulta de pedidos do usuário autenticado.
* Detalhamento de pedidos e itens.
* Controle de status por `EnumOrderStatus`.
* Controle do tipo de pagamento por `EnumPaymenType`.

### ☁️ Armazenamento de Arquivos

* Upload de imagens por meio de um provedor desacoplado (`CloudStorageProvider`).
* Integração com AWS S3 ou MinIO em ambiente local.
* Referenciamento dos arquivos persistidos pela entidade `FileReference`.
* Validação de extensões e tipos MIME com `@AllowedFileExtensions` e `@AllowedContentTypes`.

---

## 🛠️ Tecnologias Utilizadas

* **Linguagem:** Java 21
* **Framework principal:** Spring Boot 4.1.0
* **Segurança:** Spring Security e JWT (`java-jwt`)
* **Persistência:** Spring Data JPA / Hibernate
* **Banco de dados:** PostgreSQL
* **Migrações:** Flyway
* **Armazenamento:** AWS SDK S3 e MinIO
* **E-mails:** Spring Boot Starter Mail
* **Documentação:** SpringDoc OpenAPI / Swagger UI
* **Mapeamento e produtividade:** MapStruct e Lombok
* **Gerenciador de dependências:** Apache Maven

---

## 📁 Estrutura do Projeto

```text
ICPA-Ecommerce/
├── backend/
│   ├── pom.xml                         # Dependências e configuração Maven
│   ├── mvnw / mvnw.cmd                 # Maven Wrapper
│   ├── docker-compose.yaml              # MinIO para ambiente local
│   └── src/
│       ├── main/
│       │   ├── java/com/api/ICPAEcommerce/
│       │   │   ├── ICPAEcommerceApplication.java
│       │   │   ├── controllers/         # Endpoints REST
│       │   │   ├── domain/              # Entidades, enums e validações
│       │   │   ├── dto/                 # Objetos de entrada e saída da API
│       │   │   ├── infra/               # Segurança, S3, exceções e Swagger
│       │   │   ├── repositories/        # Interfaces Spring Data JPA
│       │   │   └── services/             # Regras de negócio e integrações
│       │   └── resources/
│       │       ├── application.properties
│       │       └── db/migration/         # Migrações Flyway V1, V2 e V3
│       └── test/java/com/api/ICPAEcommerce/
│           ├── domain/                  # Testes das entidades de domínio
│           └── services/                # Testes dos serviços
├── README.md
├── ESTRUTURA_PROJETO.md                 # Descrição detalhada da organização
└── LICENSE
```

### Organização das camadas

* **`controllers/`:** recebe requisições HTTP e retorna respostas da API.
* **`domain/`:** concentra entidades, enums, mapeadores e validações do domínio.
* **`dto/`:** define os contratos de entrada e saída, sem expor diretamente as entidades.
* **`services/`:** implementa regras de negócio, e-mails e operações de armazenamento.
* **`repositories/`:** abstrai o acesso às entidades persistidas com Spring Data JPA.
* **`infra/`:** contém configurações técnicas, como JWT, tratamento de exceções, S3/MinIO e OpenAPI.
* **`resources/db/migration/`:** contém o esquema inicial, usuário administrador e dados de seed.

Para uma visão detalhada dos arquivos e responsabilidades, consulte [`ESTRUTURA_PROJETO.md`](ESTRUTURA_PROJETO.md).

---

## 🔌 Endpoints da API

Os endpoints utilizam o prefixo `/api/v1`.

### 🔑 Autenticação (`/api/v1/authentication`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/api/v1/authentication/login` | Realiza login e retorna o token JWT | Público |
| `POST` | `/api/v1/authentication/forgot-password` | Solicita recuperação de senha | Público |
| `POST` | `/api/v1/authentication/change-password` | Altera a senha usando um token | Público |

### 👤 Usuários (`/api/v1/user`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/api/v1/user/register` | Cadastra um novo usuário | Público |
| `GET` | `/api/v1/user/list-users` | Lista usuários | Administrador |
| `GET` | `/api/v1/user/list-user/{userId}` | Consulta um usuário | Autenticado |
| `PUT` | `/api/v1/user/update-user/{userId}` | Atualiza um usuário | Autenticado |
| `DELETE` | `/api/v1/user/delete-user/{userId}` | Exclui um usuário | Administrador |

### 🛍️ Produtos (`/api/v1/products`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/api/v1/products/register-product` | Cadastra um produto | Administrador |
| `GET` | `/api/v1/products/list-products` | Lista produtos | Autenticado |
| `GET` | `/api/v1/products/list-products/name/{name}` | Busca produtos por nome | Autenticado |
| `GET` | `/api/v1/products/list-products/category/{category}` | Busca produtos por categoria | Autenticado |
| `GET` | `/api/v1/products/list-product/code/{code}` | Consulta produto por código | Autenticado |
| `GET` | `/api/v1/products/total-stock-value` | Consulta o valor total do estoque | Administrador |
| `PUT` | `/api/v1/products/update-product` | Atualiza um produto | Administrador |
| `DELETE` | `/api/v1/products/delete-product/{code}` | Exclui um produto | Administrador |

### 📦 Pedidos (`/api/v1/orders`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/api/v1/orders/create-order` | Cria um pedido | Autenticado |
| `GET` | `/api/v1/orders/list-orders` | Lista pedidos | Autenticado |
| `GET` | `/api/v1/orders/detail-order/{id}` | Consulta detalhes de um pedido | Autenticado |
| `PUT` | `/api/v1/orders/update-order-status/{id}` | Atualiza o status do pedido | Administrador |

### 📁 Arquivos (`/api/v1/files`)

| Método | Endpoint | Descrição | Acesso |
| --- | --- | --- | --- |
| `POST` | `/api/v1/files/images` | Envia uma imagem para o armazenamento S3/MinIO | Autenticado |

> **Observação:** os caminhos acima refletem os `@RequestMapping` e os mappings declarados nos controllers. A configuração de segurança possui alguns paths legados diferentes, como `/api/v1/users/...` (controller atual: `/api/v1/user/...`) e `/api/v1/orders/list-order/{id}` (controller atual: `/api/v1/orders/detail-order/{id}`). Esses caminhos devem ser alinhados na configuração de segurança para que as permissões aplicadas correspondam aos endpoints publicados.

---

## ⚙️ Configuração e Instalação

### 🛠️ Pré-requisitos

* **JDK 21** instalado.
* **Docker e Docker Compose** para executar o MinIO localmente.
* **PostgreSQL** acessível pela aplicação.
* **Maven 3.8+** ou o Maven Wrapper incluído no projeto.
* Credenciais de SMTP para envio de e-mails.

### 1. Clonar e acessar o projeto

```bash
git clone https://github.com/AdrianGKS/ICPA-Ecommerce.git
cd ICPA-Ecommerce/backend
```

### 2. Configurar variáveis de ambiente

O arquivo `src/main/resources/application.properties` utiliza as variáveis abaixo:

| Variável | Finalidade |
| --- | --- |
| `url_database` | URL JDBC do PostgreSQL |
| `user_database` / `password_database` | Credenciais do banco |
| `JWT_SECRET` | Chave usada para assinar tokens JWT |
| `key_id` / `key_secret` | Credenciais do armazenamento S3/MinIO |
| `mail_user` / `mail_password` | Credenciais do servidor SMTP |
| `MINIO_USER` / `MINIO_PASS` | Credenciais do container MinIO |

Exemplo de configuração para o banco:

```properties
url_database=jdbc:postgresql://localhost:5432/icpa_ecommerce
user_database=seu_usuario
password_database=sua_senha
JWT_SECRET=uma_chave_secreta
key_id=minioadmin
key_secret=minioadmin
mail_user=seu_usuario_smtp
mail_password=sua_senha_smtp
```

### 3. Iniciar o armazenamento local

Na pasta `backend/`, execute:

```bash
docker compose up -d
```

O MinIO ficará disponível em:

* **API S3:** `http://localhost:9000`
* **Console web:** `http://localhost:9001`
* **Bucket:** `icpa-ecommerce-bucket`

O serviço `minio-init` cria o bucket automaticamente e configura a política de download.

### 4. Compilar e executar a aplicação

```bash
# Linux/macOS
./mvnw clean package
./mvnw spring-boot:run

# Windows
./mvnw.cmd clean package
./mvnw.cmd spring-boot:run
```

A aplicação iniciará por padrão em `http://localhost:8080`.

---

## 🔑 Autenticação e Segurança

A API utiliza **Spring Security** com autenticação stateless baseada em **Bearer Token JWT**.

1. Faça uma requisição `POST /api/v1/authentication/login` com credenciais válidas.
2. Copie o token retornado.
3. Envie-o nas requisições protegidas:

```text
Authorization: Bearer <seu_token_jwt>
```

As permissões são controladas de acordo com o perfil do usuário, principalmente `ADMIN` e `USER`.

---

## 📄 Documentação (Swagger)

Com a aplicação em execução, acesse:

* **Swagger UI:** `http://localhost:8080/swagger-ui/index.html`
* **OpenAPI JSON:** `http://localhost:8080/v3/api-docs`

---

## 🧪 Testes

Para executar os testes automatizados:

```bash
./mvnw test
```

No Windows, utilize `./mvnw.cmd test`.

---

## 📝 Licença

Este projeto é disponibilizado sob a licença [MIT](LICENSE). Consulte o arquivo [`LICENSE`](LICENSE) para mais detalhes.
