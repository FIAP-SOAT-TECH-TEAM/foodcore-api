--liquibase formatted sql

--changeset customer:01-customer-tables

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE,
    document VARCHAR(14) UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_email_requires_name CHECK ((name IS NULL AND email IS NULL) OR (name IS NOT NULL AND email IS NOT NULL)),
    CONSTRAINT chk_requires_identification CHECK (document IS NOT NULL OR (name IS NOT NULL AND email IS NOT NULL))
);

COMMENT ON TABLE customers IS 'Tabela que armazena os dados de clientes';
COMMENT ON COLUMN customers.id IS 'Identificador único do cliente';
COMMENT ON COLUMN customers.name IS 'Nome do cliente';
COMMENT ON COLUMN customers.email IS 'Email do cliente';
COMMENT ON COLUMN customers.document IS 'DOCUMENT do cliente (apenas números ou formatado)';
COMMENT ON COLUMN customers.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN customers.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN customers.active IS 'Indica se o cliente está ativo ou não';
COMMENT ON CONSTRAINT chk_email_requires_name ON customers IS 'Garante que, se o email for informado, o nome também deve ser';
COMMENT ON CONSTRAINT chk_requires_identification ON customers IS 'Garante que ao menos um método de identificação seja informado para o cliente.';