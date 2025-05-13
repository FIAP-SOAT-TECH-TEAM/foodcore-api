--liquibase formatted sql

--changeset customer:03-customer-seed context:prod onError:MARK_RAN
-- Dados básicos para produção - apenas obrigatórios para o sistema funcionar
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT 'Cliente Anônimo', 'anonimo@fastfood.com', null, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE email = 'anonimo@fastfood.com'
);

--changeset customer:03-customer-seed-dev runAlways:true context:local,dev onError:MARK_RAN
-- Clientes para ambiente de desenvolvimento

-- João Silva - nome e email (sem CPF)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT 'João Silva', 'joao@email.com', null, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE name = 'João Silva' AND email = 'joao@email.com'
);

-- Maria Oliveira - nome e email (sem CPF)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT 'Maria Oliveira', 'maria@email.com', null, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE name = 'Maria Oliveira' AND email = 'maria@email.com'
);

-- Pedro Santos - apenas CPF (sem nome e email)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT null, null, '111.222.333-44', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '111.222.333-44'
);

-- Ana Souza - nome e email (sem CPF)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT 'Ana Souza', 'ana@email.com', null, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE name = 'Ana Souza' AND email = 'ana@email.com'
);

-- Carlos Ferreira - apenas CPF (sem nome e email)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT null, null, '999.888.777-66', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '999.888.777-66'
);

-- Lucia Pereira - nome e email (sem CPF)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT 'Lucia Pereira', 'lucia@email.com', null, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE name = 'Lucia Pereira' AND email = 'lucia@email.com'
);

-- Roberto Almeida - apenas CPF (sem nome e email)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT null, null, '777.666.555-44', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '777.666.555-44'
);

-- Julia Costa - nome e email (sem CPF)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT 'Julia Costa', 'julia@email.com', null, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE name = 'Julia Costa' AND email = 'julia@email.com'
);

-- Fernando Lima - nome e email (sem CPF)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT 'Fernando Lima', 'fernando@email.com', null, now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE name = 'Fernando Lima' AND email = 'fernando@email.com'
);

-- Amanda Rodrigues - apenas CPF (sem nome e email)
INSERT INTO customers (name, email, document, created_at, updated_at)
SELECT null, null, '333.222.111-00', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '333.222.111-00'
);