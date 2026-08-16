-- Insere o administrador padrão
-- A senha 'admin123' está criptografada em BCrypt
-- O profile '0' representa o primeiro valor do EnumUserProfile (ajuste se ADMIN for 1)

INSERT INTO users (
    id,
    name,
    email,
    password,
    profile,
    street,
    number,
    city,
    neighborhood,
    state,
    cep,
    complement
) VALUES (
             NEXTVAL('users_seq'),
             'Admin Master',
             'admin@icpa.com',
             '$2a$10$/D97EOMjsQcJ7QOaz2Z6vejGM2CMpGHrcoN5bzhX6XFmteY.S0mZ.',
             'ADMIN',
             'Rua Exemplo',
             '100',
             'Porto Alegre',
             'Rubem Berta',
             'RS',
             '91111-000',
             'Sala 1'
         );