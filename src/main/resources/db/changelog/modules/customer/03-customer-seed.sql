--liquibase formatted sql

--changeset customer-seed context:prod
-- Dados básicos para produção - apenas obrigatórios para o sistema funcionar
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
VALUES ('Cliente Anônimo', 'anonimo@fastfood.com', '00000000000', '0000000000', now(), now());

--changeset customer-seed-dev context:local,dev
-- Dados adicionais para desenvolvimento e testes
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
VALUES 
    ('Maria Santos', 'maria@email.com', '12345678901', '11987654321', now(), now()),
    ('João Silva', 'joao@email.com', '10987654321', '11912345678', now(), now()),
    ('Ana Oliveira', 'ana@email.com', '11122233344', '11955556666', now(), now()),
    ('Carlos Souza', 'carlos@email.com', '44433322211', '11977778888', now(), now()),
    ('Patrícia Lima', 'patricia@email.com', '55566677788', '11944443333', now(), now()); 