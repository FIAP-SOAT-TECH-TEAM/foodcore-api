--liquibase formatted sql

--changeset user:03-user-seed context:prod onError:MARK_RAN
-- Dados mínimos para funcionamento em produção

-- Criação do Role ADMIN
INSERT INTO roles (name, description, created_at, updated_at)
SELECT 'ADMIN', 'Acesso total ao painel administrativo', now(), now()
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

-- Criação do Role USER
INSERT INTO roles (name, description, created_at, updated_at)
SELECT 'USER', 'Usuário comum do sistema', now(), now()
    WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');

-- Criação do Role GUEST
INSERT INTO roles (name, description) VALUES ('GUEST', 'Usuário convidado')
ON CONFLICT (name) DO NOTHING;

-- Criação do usuário admin@fastfood.com
INSERT INTO users (name, username, email, password, active, role_id, created_at, updated_at)
SELECT 'Admin Sistema', 'admin', 'admin@fastfood.com', 'hashed_password_admin', true, 1, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@fastfood.com'
);


--changeset user:03-user-seed-dev runAlways:true context:local,dev onError:MARK_RAN
-- Dados para desenvolvimento/local - múltiplos usuários e roles

-- Usuários
INSERT INTO users (name, username, email, password, active, role_id, created_at, updated_at)
SELECT 'João Silva', 'joao', 'joao@email.com', 'hashed_pw_joao', true, 2,  now(), now()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'joao@email.com');

INSERT INTO users (name, username, email, password, active, role_id, created_at, updated_at)
SELECT 'Maria Oliveira', 'maria', 'maria@email.com', 'hashed_pw_maria', true, 2, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'maria@email.com');

INSERT INTO users (name, username, email, password, active, role_id, created_at, updated_at)
SELECT 'Ana Souza', 'ana', 'ana@email.com', 'hashed_pw_ana', true, 2, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'ana@email.com');

