--liquibase formatted sql

--changeset product-seed context:local,dev,prod
-- Insere categorias básicas para produção
INSERT INTO categories (name, description, image_url, active, created_at, updated_at)
VALUES ('Lanche', 'Hambúrgueres e sanduíches', 'http://localhost:8082/categories/placeholder.jpg', true, now(), now()),
       ('Acompanhamento', 'Batatas fritas e outros acompanhamentos', 'http://localhost:8082/categories/placeholder.jpg', true, now(), now()),
       ('Bebida', 'Refrigerantes, sucos e outras bebidas', 'http://localhost:8082/categories/placeholder.jpg', true, now(), now()),
       ('Sobremesa', 'Sorvetes, tortas e outras sobremesas', 'http://localhost:8082/categories/placeholder.jpg', true, now(), now());

--changeset product-seed-dev context:local,dev
-- Insere produtos básicos para produção
INSERT INTO products (name, description, price, category_id, image_url, active, created_at, updated_at)
VALUES ('X-Burger', 'Hambúrguer com queijo', 10.00, (SELECT id FROM categories WHERE name = 'Lanche'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Batata Frita P', 'Porção pequena de batata frita', 5.00, (SELECT id FROM categories WHERE name = 'Acompanhamento'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Refrigerante', 'Lata de refrigerante 350ml', 6.00, (SELECT id FROM categories WHERE name = 'Bebida'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Sorvete', 'Casquinha de baunilha', 4.00, (SELECT id FROM categories WHERE name = 'Sobremesa'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now());

--changeset product-seed-dev-additional context:local,dev
-- Insere produtos adicionais para desenvolvimento e testes
INSERT INTO products (name, description, price, category_id, image_url, active, created_at, updated_at)
VALUES ('X-Tudo', 'Hambúrguer completo com tudo', 20.00, (SELECT id FROM categories WHERE name = 'Lanche'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('X-Salada', 'Hambúrguer com salada', 12.00, (SELECT id FROM categories WHERE name = 'Lanche'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('X-Bacon', 'Hambúrguer com bacon', 15.00, (SELECT id FROM categories WHERE name = 'Lanche'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Batata Frita G', 'Porção grande de batata frita', 8.00, (SELECT id FROM categories WHERE name = 'Acompanhamento'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Onion Rings', 'Anéis de cebola empanados', 7.00, (SELECT id FROM categories WHERE name = 'Acompanhamento'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Suco Natural', 'Suco de laranja natural 300ml', 7.00, (SELECT id FROM categories WHERE name = 'Bebida'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Milk Shake', 'Milk shake de chocolate 400ml', 12.00, (SELECT id FROM categories WHERE name = 'Bebida'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Sundae', 'Sundae de chocolate com calda e amendoim', 8.00, (SELECT id FROM categories WHERE name = 'Sobremesa'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Torta de Limão', 'Fatia de torta de limão', 6.00, (SELECT id FROM categories WHERE name = 'Sobremesa'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('X-Promocional', 'Hambúrguer promocional', 8.00, (SELECT id FROM categories WHERE name = 'Lanche'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()),
       ('Combo Família', 'Combo com 4 lanches, 2 batatas grandes e 4 refrigerantes', 65.00, (SELECT id FROM categories WHERE name = 'Lanche'), 'http://localhost:8082/products/placeholder.jpg', true, now(), now()); 