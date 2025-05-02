--liquibase formatted sql

--changeset payment-seed context:local,dev
-- Os dados de pagamentos são apenas para ambiente de desenvolvimento
-- Pagamento para o pedido 1
INSERT INTO payments (order_id, status, type, amount, created_at, updated_at)
SELECT 
    id, 
    'APPROVED', 
    'CREDIT_CARD', 
    total, 
    created_at, 
    updated_at 
FROM orders 
WHERE id = (
    SELECT id FROM orders 
    ORDER BY id 
    LIMIT 1
);

-- Pagamento para o pedido 2
INSERT INTO payments (order_id, status, type, amount, created_at, updated_at)
SELECT 
    id, 
    'APPROVED', 
    'DEBIT_CARD', 
    total, 
    created_at, 
    updated_at 
FROM orders 
WHERE id = (
    SELECT id FROM orders 
    ORDER BY id 
    OFFSET 1
    LIMIT 1
);

-- Pagamento para o pedido 3
INSERT INTO payments (order_id, status, type, amount, created_at, updated_at)
SELECT 
    id, 
    'APPROVED', 
    'PIX', 
    total, 
    created_at, 
    updated_at 
FROM orders 
WHERE id = (
    SELECT id FROM orders 
    ORDER BY id 
    OFFSET 2
    LIMIT 1
);

-- Pagamento para o pedido 4
INSERT INTO payments (order_id, status, type, amount, created_at, updated_at)
SELECT 
    id, 
    'PENDING', 
    'CASH', 
    total, 
    created_at, 
    updated_at 
FROM orders 
WHERE id = (
    SELECT id FROM orders 
    ORDER BY id 
    OFFSET 3
    LIMIT 1
); 