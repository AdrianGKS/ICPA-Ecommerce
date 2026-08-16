-- Criação das Sequências para os IDs
CREATE SEQUENCE users_seq INCREMENT BY 50;
CREATE SEQUENCE password_reset_tokens_seq INCREMENT BY 50;
CREATE SEQUENCE orders_seq INCREMENT BY 50;
CREATE SEQUENCE products_seq INCREMENT BY 50;
CREATE SEQUENCE file_reference_seq INCREMENT BY 50;

-- 1. Criação da Tabela Users (Sem chaves estrangeiras)
CREATE TABLE users (
                       id BIGINT NOT NULL PRIMARY KEY,
                       name VARCHAR(255),
                       email VARCHAR(255),
                       password VARCHAR(255),
                       profile VARCHAR(255),
                       street VARCHAR(255),
                       number VARCHAR(255),
                       city VARCHAR(255),
                       neighborhood VARCHAR(255),
                       state VARCHAR(255),
                       cep VARCHAR(255),
                       complement VARCHAR(255)
);

-- 2. Criação da Tabela Password_Reset_Tokens (Depende de Users)
CREATE TABLE password_reset_tokens (
                                       id BIGINT NOT NULL PRIMARY KEY,
                                       token VARCHAR(255) NOT NULL UNIQUE,
                                       user_id BIGINT NOT NULL,
                                       created_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                       expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
                                       used BOOLEAN NOT NULL DEFAULT FALSE,
                                       CONSTRAINT fk_password_reset_token_user FOREIGN KEY (user_id) REFERENCES users(id)
);

-- 3. Criação da Tabela Orders (Sem chaves estrangeiras diretas na própria tabela)
CREATE TABLE orders (
                        id BIGINT NOT NULL PRIMARY KEY,
                        client_email VARCHAR(255),
                        order_date TIMESTAMP WITH TIME ZONE,
                        order_price DOUBLE PRECISION,
                        payment_type SMALLINT,
                        status VARCHAR(255),
                        street VARCHAR(255),
                        number VARCHAR(255),
                        city VARCHAR(255),
                        neighborhood VARCHAR(255),
                        state VARCHAR(255),
                        cep VARCHAR(255),
                        complement VARCHAR(255)
);

-- 4. Criação da Tabela Products (Depende de Orders)
CREATE TABLE products (
                          id BIGINT NOT NULL PRIMARY KEY,
                          code VARCHAR(255),
                          name VARCHAR(255),
                          description VARCHAR(255),
                          price NUMERIC(19, 2),
                          quantity INTEGER,
                          enum_product_category VARCHAR(255),
                          order_id BIGINT,
                          CONSTRAINT fk_product_order FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- 5. Criação da Tabela File_Reference (Depende de Products)
CREATE TABLE file_reference (
                                id BIGINT NOT NULL PRIMARY KEY,
                                product_id BIGINT,
                                created_at TIMESTAMP WITH TIME ZONE,
                                temp BOOLEAN,
                                type VARCHAR(255),
                                name VARCHAR(255),
                                content_type VARCHAR(255),
                                content_length BIGINT,
                                CONSTRAINT fk_file_reference_product FOREIGN KEY (product_id) REFERENCES products(id)
);