--liquibase formatted sql

--changeset customer:03-customer-seed context:prod onError:MARK_RAN
-- Dados básicos para produção - apenas obrigatórios para o sistema funcionar
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Cliente Anônimo', 'anonimo@fastfood.com', '00000000000', '0000000000', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '00000000000'
);

--changeset customer:customer-seed-dev runAlways:true context:local,dev onError:MARK_RAN
-- Os dados de clientes são apenas para ambiente de desenvolvimento
-- João Silva
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'João Silva', 'joao@email.com', '123.456.789-00', '11 99999-0001', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '123.456.789-00'
);

-- Maria Oliveira
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Maria Oliveira', 'maria@email.com', '987.654.321-00', '11 99999-0002', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '987.654.321-00'
);

-- Pedro Santos
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Pedro Santos', 'pedro@email.com', '111.222.333-44', '11 99999-0003', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '111.222.333-44'
);

-- Ana Souza
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Ana Souza', 'ana@email.com', '555.666.777-88', '11 99999-0004', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '555.666.777-88'
);

-- Carlos Ferreira
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Carlos Ferreira', 'carlos@email.com', '999.888.777-66', '11 99999-0005', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '999.888.777-66'
);

-- Lucia Pereira
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Lucia Pereira', 'lucia@email.com', '444.333.222-11', '11 99999-0006', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '444.333.222-11'
);

-- Roberto Almeida
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Roberto Almeida', 'roberto@email.com', '777.666.555-44', '11 99999-0007', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '777.666.555-44'
);

-- Julia Costa
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Julia Costa', 'julia@email.com', '222.333.444-55', '11 99999-0008', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '222.333.444-55'
);

-- Fernando Lima
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Fernando Lima', 'fernando@email.com', '888.999.000-11', '11 99999-0009', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '888.999.000-11'
);

-- Amanda Rodrigues
INSERT INTO customers (name, email, document, phone, created_at, updated_at)
SELECT 'Amanda Rodrigues', 'amanda@email.com', '333.222.111-00', '11 99999-0010', now(), now()
WHERE NOT EXISTS (
    SELECT 1 FROM customers WHERE document = '333.222.111-00'
); 