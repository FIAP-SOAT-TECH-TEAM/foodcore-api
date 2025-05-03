--liquibase formatted sql

--changeset customer:01-customer-tables

-- Tabela de Clientes
CREATE TABLE customers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    document VARCHAR(14) NOT NULL UNIQUE,
    phone VARCHAR(20),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

COMMENT ON TABLE customers IS 'Tabela que armazena os dados de clientes';
COMMENT ON COLUMN customers.id IS 'Identificador único do cliente';
COMMENT ON COLUMN customers.name IS 'Nome do cliente';
COMMENT ON COLUMN customers.email IS 'Email do cliente';
COMMENT ON COLUMN customers.document IS 'DOCUMENT do cliente (apenas números ou formatado)';
COMMENT ON COLUMN customers.phone IS 'Telefone do cliente';
COMMENT ON COLUMN customers.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN customers.updated_at IS 'Data da última atualização do registro';
COMMENT ON COLUMN customers.active IS 'Indica se o cliente está ativo ou não';