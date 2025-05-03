--liquibase formatted sql

--changeset order:03-order-seed context:local,dev
-- Os dados de pedidos são apenas para ambiente de desenvolvimento
-- Pedido 1
INSERT INTO orders (customer_id, status, total, created_at, updated_at)
VALUES (
    (SELECT id FROM customers WHERE email = 'maria@email.com' LIMIT 1), 
    'COMPLETED', 
    32.80, 
    now() - interval '2 hour', 
    now() - interval '1 hour'
);

INSERT INTO order_items (order_id, product_id, quantity, unit_price, total, created_at, updated_at)
VALUES (
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'X-Burger' LIMIT 1),
    1,
    22.90,
    22.90,
    now() - interval '2 hour',
    now() - interval '2 hour'
),
(
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'Refrigerante Lata' LIMIT 1),
    1,
    6.90,
    6.90,
    now() - interval '2 hour',
    now() - interval '2 hour'
),
(
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'Batata Frita P' LIMIT 1),
    1,
    9.90,
    9.90,
    now() - interval '2 hour',
    now() - interval '2 hour'
);

-- Pedido 2
INSERT INTO orders (customer_id, status, total, created_at, updated_at)
VALUES (
    (SELECT id FROM customers WHERE email = 'joao@email.com' LIMIT 1), 
    'PREPARING', 
    45.70, 
    now() - interval '45 minute', 
    now() - interval '30 minute'
);

INSERT INTO order_items (order_id, product_id, quantity, unit_price, total, created_at, updated_at)
VALUES (
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'X-Tudo' LIMIT 1),
    1,
    28.90,
    28.90,
    now() - interval '45 minute',
    now() - interval '45 minute'
),
(
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'Batata Frita P' LIMIT 1),
    1,
    9.90,
    9.90,
    now() - interval '45 minute',
    now() - interval '45 minute'
),
(
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'Refrigerante Lata' LIMIT 1),
    1,
    6.90,
    6.90,
    now() - interval '45 minute',
    now() - interval '45 minute'
);

-- Pedido 3
INSERT INTO orders (customer_id, status, total, created_at, updated_at)
VALUES (
    (SELECT id FROM customers WHERE email = 'ana@email.com' LIMIT 1), 
    'READY', 
    27.80, 
    now() - interval '20 minute', 
    now() - interval '10 minute'
);

INSERT INTO order_items (order_id, product_id, quantity, unit_price, total, created_at, updated_at)
VALUES (
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'X-Salada' LIMIT 1),
    1,
    20.90,
    20.90,
    now() - interval '20 minute',
    now() - interval '20 minute'
),
(
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'Refrigerante Lata' LIMIT 1),
    1,
    6.90,
    6.90,
    now() - interval '20 minute',
    now() - interval '20 minute'
);

-- Pedido 4 - Em andamento
INSERT INTO orders (customer_id, status, total, created_at, updated_at)
VALUES (
    (SELECT id FROM customers WHERE email = 'carlos@email.com' LIMIT 1), 
    'RECEIVED', 
    36.70, 
    now() - interval '5 minute', 
    now() - interval '5 minute'
);

INSERT INTO order_items (order_id, product_id, quantity, unit_price, total, created_at, updated_at)
VALUES (
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'X-Bacon' LIMIT 1),
    1,
    24.90,
    24.90,
    now() - interval '5 minute',
    now() - interval '5 minute'
),
(
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'Sorvete' LIMIT 1),
    1,
    7.90,
    7.90,
    now() - interval '5 minute',
    now() - interval '5 minute'
),
(
    (SELECT id FROM orders ORDER BY id DESC LIMIT 1),
    (SELECT id FROM products WHERE name = 'Água Mineral' LIMIT 1),
    1,
    4.90,
    4.90,
    now() - interval '5 minute',
    now() - interval '5 minute'
);