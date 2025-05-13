--liquibase formatted sql

--changeset user:01-user-tables runAlways:true
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    active BOOLEAN DEFAULT TRUE NOT NULL,
    last_login TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE users IS 'Tabela de usuários do sistema';
COMMENT ON COLUMN users.id IS 'ID único do usuário';
COMMENT ON COLUMN users.username IS 'Nome de usuário único para login';
COMMENT ON COLUMN users.email IS 'E-mail do usuário (também único)';
COMMENT ON COLUMN users.password_hash IS 'Hash da senha do usuário';
COMMENT ON COLUMN users.active IS 'Indica se o usuário está ativo';
COMMENT ON COLUMN users.last_login IS 'Data do último login';
COMMENT ON COLUMN users.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN users.updated_at IS 'Data da última atualização do registro';

--changeset user:02-user-tables runAlways:true
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE roles IS 'Tabela de roles/perfis do sistema';
COMMENT ON COLUMN roles.id IS 'ID único da Role';
COMMENT ON COLUMN roles.name IS 'Nome único do role (ex: ADMIN, USER)';
COMMENT ON COLUMN roles.description IS 'Descrição das permissões do role';
COMMENT ON COLUMN roles.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN roles.updated_at IS 'Data da última atualização do registro';

--changeset user:03-user-tables runAlways:true
CREATE TABLE IF NOT EXISTS user_roles (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_roles_role FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT un_user_role UNIQUE (user_id, role_id)
);

COMMENT ON TABLE user_roles IS 'Tabela de relacionamento entre usuários e roles';
COMMENT ON COLUMN user_roles.id IS 'ID único da Role do usuário';
COMMENT ON COLUMN user_roles.user_id IS 'ID do usuário';
COMMENT ON COLUMN user_roles.role_id IS 'ID do role associado';
COMMENT ON COLUMN user_roles.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN user_roles.updated_at IS 'Data da última atualização do registro';
COMMENT ON CONSTRAINT un_user_role ON user_roles IS 'Garante que um usuário não tenha o mesmo role atribuído mais de uma vez';

--changeset user:04-user-tables runAlways:true
CREATE TABLE IF NOT EXISTS refresh_tokens (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    token VARCHAR(255) UNIQUE NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_refresh_tokens_user FOREIGN KEY (user_id) REFERENCES users(id)
);

COMMENT ON TABLE refresh_tokens IS 'Tabela de tokens de refresh para JWT';
COMMENT ON COLUMN refresh_tokens.id IS 'ID único do refresh token';
COMMENT ON COLUMN refresh_tokens.token IS 'Token de refresh único';
COMMENT ON COLUMN refresh_tokens.expires_at IS 'Data de expiração do token';
COMMENT ON COLUMN refresh_tokens.active IS 'Indica se o Refresh Token está ativo (logoff invalida o refresh token)';
COMMENT ON COLUMN refresh_tokens.created_at IS 'Data de criação do registro';