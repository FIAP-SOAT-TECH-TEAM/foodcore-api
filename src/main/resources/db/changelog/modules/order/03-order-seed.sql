--liquibase formatted sql

--changeset order:03-order-seed runAlways:true context:local,dev onError:MARK_RAN
-- Os dados de pedidos são apenas para ambiente de desenvolvimento

-- Pedido 1
INSERT INTO orders (customer_id, order_number, status, total, created_at, updated_at)
SELECT
    (SELECT id FROM customers WHERE email = 'maria@email.com' LIMIT 1),
    'ORD-00000001',
    'COMPLETED',
    32.80,
    now() - interval '2 hour',
    now() - interval '1 hour'
WHERE NOT EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000001'
);

-- Itens do pedido 1
-- X-Burger
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, observations, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000001'),
    (SELECT id FROM products WHERE name = 'X-Burger'),
    1,
    22.90,
    22.90,
    'Sem cebola',
    now() - interval '2 hour',
    now() - interval '2 hour'
WHERE NOT EXISTS (
    SELECT 1 FROM order_items
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000001')
    AND product_id = (SELECT id FROM products WHERE name = 'X-Burger')
);

-- Refrigerante Lata
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, observations, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000001'),
    (SELECT id FROM products WHERE name = 'Refrigerante Lata'),
    1,
    6.90,
    6.90,
    null,
    now() - interval '2 hour',
    now() - interval '2 hour'
WHERE NOT EXISTS (
    SELECT 1 FROM order_items
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000001')
    AND product_id = (SELECT id FROM products WHERE name = 'Refrigerante Lata')
);

-- Pagamento do pedido 1
INSERT INTO order_payments (order_id, payment_id, tid, status, amount, paid_at, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000001'),
    (SELECT id FROM payments LIMIT 1),
    'TID-000001',
    'APPROVED',
    3280,
    now() - interval '1 hour',
    now() - interval '1 hour',
    now() - interval '1 hour'
WHERE NOT EXISTS (
    SELECT 1 FROM order_payments WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000001')
);

-- Pedido 2
INSERT INTO orders (customer_id, order_number, status, total, created_at, updated_at)
SELECT
    (SELECT id FROM customers WHERE email = 'joao@email.com'),
    'ORD-00000002',
    'PREPARING',
    79.70,
    now() - interval '30 minute',
    now() - interval '25 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000002'
);

-- X-Salada
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, observations, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000002'),
    (SELECT id FROM products WHERE name = 'X-Salada'),
    2,
    20.90,
    41.80,
    null,
    now() - interval '30 minute',
    now() - interval '30 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM order_items
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000002')
    AND product_id = (SELECT id FROM products WHERE name = 'X-Salada')
);

-- X-Bacon
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, observations, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000002'),
    (SELECT id FROM products WHERE name = 'X-Bacon'),
    1,
    24.90,
    24.90,
    null,
    now() - interval '30 minute',
    now() - interval '30 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM order_items
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000002')
    AND product_id = (SELECT id FROM products WHERE name = 'X-Bacon')
);

-- Refrigerante Lata
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, observations, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000002'),
    (SELECT id FROM products WHERE name = 'Refrigerante Lata'),
    2,
    6.90,
    13.80,
    null,
    now() - interval '30 minute',
    now() - interval '30 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM order_items
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000002')
    AND product_id = (SELECT id FROM products WHERE name = 'Refrigerante Lata')
);

-- Pagamento do pedido 2
INSERT INTO order_payments (order_id, payment_id, tid, status, amount, paid_at, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000002'),
    (SELECT id FROM payments LIMIT 1 OFFSET 1),
    'TID-000002',
    'REJECTED',
    7970,
    null,
    now() - interval '25 minute',
    now() - interval '25 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM order_payments WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000002')
);

-- Pedido 3 (sem cliente)
INSERT INTO orders (order_number, status, total, created_at, updated_at)
SELECT
    'ORD-00000003',
    'RECEIVED',
    19.90,
    now() - interval '5 minute',
    now() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000003'
);

-- Água Mineral
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, observations, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000003'),
    (SELECT id FROM products WHERE name = 'Água Mineral'),
    1,
    4.90,
    4.90,
    null,
    now() - interval '5 minute',
    now() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM order_items
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000003')
    AND product_id = (SELECT id FROM products WHERE name = 'Água Mineral')
);

-- Batata Frita G
INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal, observations, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000003'),
    (SELECT id FROM products WHERE name = 'Batata Frita G'),
    1,
    15.00,
    15.00,
    null,
    now() - interval '5 minute',
    now() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM order_items
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000003')
    AND product_id = (SELECT id FROM products WHERE name = 'Batata Frita G')
);

-- Pagamento do pedido 3
INSERT INTO order_payments (order_id, payment_id, tid, status, amount, paid_at, created_at, updated_at)
SELECT
    (SELECT id FROM orders WHERE order_number = 'ORD-00000003'),
    (SELECT id FROM payments LIMIT 1 OFFSET 2),
    'TID-000003',
    'PENDING',
    1990,
    null,
    now() - interval '5 minute',
    now() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM order_payments WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000003')
);
