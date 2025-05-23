--liquibase formatted sql

--changeset user:03-user-seed context:prod onError:MARK_RAN
-- Dados mínimos para funcionamento em produção

-- Criação do Role ADMIN
INSERT INTO roles (name, description, created_at, updated_at)
SELECT 'ADMIN', 'Acesso completo ao sistema (painel administrativo)', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM roles WHERE name = 'ADMIN'
);

-- Criação do usuário admin@fastfood.com
INSERT INTO users (username, email, password_hash, name, active, role_id, created_at, updated_at)
SELECT 'admin', 'admin@fastfood.com', 'hashed_password_admin', 'Admin Sistema', true, 1, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM users WHERE email = 'admin@fastfood.com'
);


--changeset user:03-user-seed-dev runAlways:true context:local,dev onError:MARK_RAN
-- Dados para desenvolvimento/local - múltiplos usuários e roles

-- Roles
INSERT INTO roles (name, description, created_at, updated_at)
SELECT 'ADMIN', 'Acesso total ao painel administrativo', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN');

INSERT INTO roles (name, description, created_at, updated_at)
SELECT 'USER', 'Usuário comum do sistema', now(), now()
WHERE NOT EXISTS (SELECT 1 FROM roles WHERE name = 'USER');

-- Usuários
INSERT INTO users (username, email, password_hash, name, active, role_id, created_at, updated_at)
SELECT 'joao', 'joao@email.com', 'hashed_pw_joao', 'João Silva', true, 2,  now(), now()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'joao@email.com');

INSERT INTO users (username, email, password_hash, name, active, role_id created_at, updated_at)
SELECT 'maria', 'maria@email.com', 'hashed_pw_maria', 'Maria Oliveira', true, 2, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'maria@email.com');

INSERT INTO users (username, email, password_hash, name, active, role_id created_at, updated_at)
SELECT 'ana', 'ana@email.com', 'hashed_pw_ana', 'Ana Souza', true, 2, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'ana@email.com');

INSERT INTO users (username, email, password_hash, name, active, role_id created_at, updated_at)
SELECT 'admin_local', 'admin@local.com', 'hashed_pw_admin', 'Admin Local', true, 1, now(), now()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@local.com');

