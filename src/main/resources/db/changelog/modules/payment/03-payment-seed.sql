--liquibase formatted sql

--changeset payment:03-payment-seed context:local,dev runAlways:true onError:MARK_RAN
-- Os dados de pagamentos são apenas para ambiente de desenvolvimento
-- Pagamento para o pedido 1
INSERT INTO payments (order_id, status, type, amount, created_at, updated_at)
SELECT 
    o.id, 
    'APPROVED', 
    'CREDIT_CARD', 
    o.total, 
    o.created_at, 
    o.updated_at 
FROM orders o
WHERE o.order_number = 'ORD-00000001'
AND NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.order_id = o.id
);

-- Pagamento para o pedido 2
INSERT INTO payments (order_id, status, type, amount, created_at, updated_at)
SELECT 
    o.id, 
    'APPROVED', 
    'DEBIT_CARD', 
    o.total, 
    o.created_at, 
    o.updated_at 
FROM orders o
WHERE o.order_number = 'ORD-00000002'
AND NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.order_id = o.id
);

-- Pagamento para o pedido 3
INSERT INTO payments (order_id, status, type, amount, created_at, updated_at)
SELECT 
    o.id, 
    'APPROVED', 
    'PIX', 
    o.total, 
    o.created_at, 
    o.updated_at 
FROM orders o
WHERE o.order_number = 'ORD-00000003'
AND NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.order_id = o.id
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