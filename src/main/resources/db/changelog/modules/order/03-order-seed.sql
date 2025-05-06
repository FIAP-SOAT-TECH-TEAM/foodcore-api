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

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total, observations, created_at, updated_at)
SELECT 
    (SELECT id FROM orders WHERE order_number = 'ORD-00000001' LIMIT 1),
    (SELECT id FROM products WHERE name = 'X-Burger' LIMIT 1),
    'X-Burger',
    1,
    22.90,
    22.90,
    'Sem cebola',
    now() - interval '2 hour',
    now() - interval '2 hour'
WHERE EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000001'
) AND NOT EXISTS (
    SELECT 1 FROM order_items 
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000001' LIMIT 1)
    AND product_name = 'X-Burger'
);

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total, observations, created_at, updated_at)
SELECT 
    (SELECT id FROM orders WHERE order_number = 'ORD-00000001' LIMIT 1),
    (SELECT id FROM products WHERE name = 'Refrigerante Lata' LIMIT 1),
    'Refrigerante Lata',
    1,
    6.90,
    6.90,
    null,
    now() - interval '2 hour',
    now() - interval '2 hour'
WHERE EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000001'
) AND NOT EXISTS (
    SELECT 1 FROM order_items 
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000001' LIMIT 1)
    AND product_name = 'Refrigerante Lata'
);

-- Pedido 2
INSERT INTO orders (customer_id, order_number, status, total, created_at, updated_at)
SELECT 
    (SELECT id FROM customers WHERE email = 'joao@email.com' LIMIT 1), 
    'ORD-00000002',
    'PREPARING', 
    79.70, 
    now() - interval '30 minute', 
    now() - interval '25 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000002'
);

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total, observations, created_at, updated_at)
SELECT 
    (SELECT id FROM orders WHERE order_number = 'ORD-00000002' LIMIT 1),
    (SELECT id FROM products WHERE name = 'X-Salada' LIMIT 1),
    'X-Salada',
    2,
    24.90,
    49.80,
    'Um sem tomate, outro completo',
    now() - interval '30 minute',
    now() - interval '30 minute'
WHERE EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000002'
) AND NOT EXISTS (
    SELECT 1 FROM order_items 
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000002' LIMIT 1)
    AND product_name = 'X-Salada'
);

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total, observations, created_at, updated_at)
SELECT 
    (SELECT id FROM orders WHERE order_number = 'ORD-00000002' LIMIT 1),
    (SELECT id FROM products WHERE name = 'Batata Frita P' LIMIT 1),
    'Batata Frita P',
    1,
    9.90,
    9.90,
    'Bem passada',
    now() - interval '30 minute',
    now() - interval '30 minute'
WHERE EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000002'
) AND NOT EXISTS (
    SELECT 1 FROM order_items 
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000002' LIMIT 1)
    AND product_name = 'Batata Frita P'
);

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total, observations, created_at, updated_at)
SELECT 
    (SELECT id FROM orders WHERE order_number = 'ORD-00000002' LIMIT 1),
    (SELECT id FROM products WHERE name = 'Refrigerante 600ml' LIMIT 1),
    'Refrigerante 600ml',
    1,
    10.00,
    10.00,
    null,
    now() - interval '30 minute',
    now() - interval '30 minute'
WHERE EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000002'
) AND NOT EXISTS (
    SELECT 1 FROM order_items 
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000002' LIMIT 1)
    AND product_name = 'Refrigerante 600ml'
);

-- Pedido 3 (anônimo - sem cliente)
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

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total, observations, created_at, updated_at)
SELECT 
    (SELECT id FROM orders WHERE order_number = 'ORD-00000003' LIMIT 1),
    (SELECT id FROM products WHERE name = 'Água Mineral' LIMIT 1),
    'Água Mineral',
    1,
    4.90,
    4.90,
    'Com gás',
    now() - interval '5 minute',
    now() - interval '5 minute'
WHERE EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000003'
) AND NOT EXISTS (
    SELECT 1 FROM order_items 
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000003' LIMIT 1)
    AND product_name = 'Água Mineral'
);

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, total, observations, created_at, updated_at)
SELECT 
    (SELECT id FROM orders WHERE order_number = 'ORD-00000003' LIMIT 1),
    (SELECT id FROM products WHERE name = 'Batata Frita G' LIMIT 1),
    'Batata Frita G',
    1,
    15.00,
    15.00,
    null,
    now() - interval '5 minute',
    now() - interval '5 minute'
WHERE EXISTS (
    SELECT 1 FROM orders WHERE order_number = 'ORD-00000003'
) AND NOT EXISTS (
    SELECT 1 FROM order_items 
    WHERE order_id = (SELECT id FROM orders WHERE order_number = 'ORD-00000003' LIMIT 1)
    AND product_name = 'Batata Frita G'
);