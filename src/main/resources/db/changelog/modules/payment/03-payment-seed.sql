--liquibase formatted sql

--changeset payment:03-payment-seed context:local,dev runAlways:true onError:MARK_RAN
-- Os dados de pagamentos são apenas para ambiente de desenvolvimento

-- Pagamento para o pedido 1 (Cartão de Crédito)
INSERT INTO payments (customer_id, type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    o.customer_id,
    'CREDIT_CARD',
    NOW() + interval '30 days',
    o.total,
    NULL,
    'Pagamento aprovado via cartão',
    o.created_at,
    o.updated_at
FROM orders o
WHERE o.order_number = 'ORD-00000001'
AND NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.customer_id = o.customer_id AND p.created_at::date = o.created_at::date
);

-- Pagamento para o pedido 2 (Débito)
INSERT INTO payments (customer_id, type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    o.customer_id,
    'DEBIT_CARD',
    NOW() + interval '1 hour',
    o.total,
    NULL,
    'Pagamento via débito automático',
    o.created_at,
    o.updated_at
FROM orders o
WHERE o.order_number = 'ORD-00000002'
AND NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.customer_id = o.customer_id AND p.created_at::date = o.created_at::date
);

-- Pagamento para o pedido 3 (PIX) - sem cliente
INSERT INTO payments (type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    o.total,
    'https://pix.example.com/qr/123456',
    'QR Code gerado para pagamento',
    o.created_at,
    o.updated_at
FROM orders o
WHERE o.order_number = 'ORD-00000003'
AND NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.amount = o.total AND p.created_at::date = o.created_at::date AND p.customer_id IS NULL
);


-- Pagamento para o pedido 4 (Dinheiro)
INSERT INTO payments (customer_id, type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    o.customer_id,
    'CASH',
    NOW() + interval '24 hour',
    o.total,
    NULL,
    'Pagamento em dinheiro na entrega',
    o.created_at,
    o.updated_at
FROM orders o
WHERE o.id = (
    SELECT id FROM orders
    ORDER BY id
    OFFSET 3
    LIMIT 1
)
AND NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.customer_id = o.customer_id AND p.created_at::date = o.created_at::date
);