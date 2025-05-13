--liquibase formatted sql

--changeset customer:02-customer-indexes

-- Índices para tabela de clientes
CREATE INDEX IF NOT EXISTS idx_customers_email ON customers(email);
CREATE INDEX IF NOT EXISTS idx_customers_document ON customers(document);
CREATE INDEX IF NOT EXISTS idx_customers_active ON customers(active);