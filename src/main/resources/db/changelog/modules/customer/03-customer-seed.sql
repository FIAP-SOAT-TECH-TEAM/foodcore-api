--liquibase formatted sql

--changeset customer-seed context:prod
-- Dados básicos para produção - apenas obrigatórios para o sistema funcionar
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
VALUES ('Cliente Anônimo', 'anonimo@fastfood.com', '00000000000', '0000000000', now(), now());

--changeset customer-seed-dev context:local,dev
-- Dados iniciais para clientes
INSERT INTO customers (name, email, document, phone, created_at, updated_at, active)
VALUES
    ('João Silva', 'joao.silva@example.com', '123.456.789-01', '(11) 98765-4321', NOW(), NOW(), true),
    ('Maria Santos', 'maria.santos@example.com', '987.654.321-09', '(11) 91234-5678', NOW(), NOW(), true),
    ('Pedro Oliveira', 'pedro.oliveira@example.com', '456.789.123-45', '(11) 99876-5432', NOW(), NOW(), true),
    ('Ana Souza', 'ana.souza@example.com', '789.123.456-78', '(11) 95678-1234', NOW(), NOW(), true); 