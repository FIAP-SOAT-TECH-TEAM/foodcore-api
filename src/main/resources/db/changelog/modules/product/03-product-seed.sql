--liquibase formatted sql

--changeset product:03-product-seed

-- Dados iniciais para categorias
INSERT INTO categories (name, description, image_url, display_order, active) VALUES
('Lanches', 'Hamburgueres e sanduíches', 'categories/burgers.jpg', 1, true),
('Acompanhamentos', 'Batatas fritas, nuggets e outros acompanhamentos', 'categories/sides.jpg', 2, true),
('Bebidas', 'Refrigerantes, sucos e outras bebidas', 'categories/drinks.jpg', 3, true),
('Sobremesas', 'Sorvetes, tortas e outras sobremesas', 'categories/desserts.jpg', 4, true);

-- Dados iniciais para produtos
-- Lanches
INSERT INTO products (name, description, price, image_url, category_id, display_order, active) VALUES
('X-Burger', 'Hambúrguer, queijo, alface, tomate, cebola e molho especial', 22.90, 'products/x-burger.jpg', 1, 1, true),
('X-Bacon', 'Hambúrguer, queijo, bacon, alface, tomate e molho especial', 24.90, 'products/x-bacon.jpg', 1, 2, true),
('X-Salada', 'Hambúrguer, queijo, alface, tomate, cebola, picles e molho especial', 20.90, 'products/x-salada.jpg', 1, 3, true),
('X-Tudo', 'Hambúrguer, queijo, bacon, ovo, alface, tomate, cebola e molho especial', 28.90, 'products/x-tudo.jpg', 1, 4, true);

-- Acompanhamentos
INSERT INTO products (name, description, price, image_url, category_id, display_order, active) VALUES
('Batata Frita P', 'Porção pequena de batata frita', 9.90, 'products/fries-small.jpg', 2, 1, true),
('Batata Frita M', 'Porção média de batata frita', 12.90, 'products/fries-medium.jpg', 2, 2, true),
('Batata Frita G', 'Porção grande de batata frita', 15.90, 'products/fries-large.jpg', 2, 3, true),
('Onion Rings', 'Anéis de cebola empanados', 14.90, 'products/onion-rings.jpg', 2, 4, true),
('Nuggets', 'Porção com 6 unidades de nuggets de frango', 12.90, 'products/nuggets.jpg', 2, 5, true);

-- Bebidas
INSERT INTO products (name, description, price, image_url, category_id, display_order, active) VALUES
('Refrigerante Lata', 'Coca-Cola, Guaraná, Fanta ou Sprite - 350ml', 6.90, 'products/soda-can.jpg', 3, 1, true),
('Refrigerante 600ml', 'Coca-Cola, Guaraná, Fanta ou Sprite - 600ml', 9.90, 'products/soda-bottle.jpg', 3, 2, true),
('Suco Natural', 'Laranja, Limão, Abacaxi ou Maracujá - 400ml', 8.90, 'products/natural-juice.jpg', 3, 3, true),
('Água Mineral', 'Sem gás - 500ml', 4.90, 'products/water.jpg', 3, 4, true),
('Água Mineral com Gás', 'Com gás - 500ml', 5.90, 'products/sparkling-water.jpg', 3, 5, true);

-- Sobremesas
INSERT INTO products (name, description, price, image_url, category_id, display_order, active) VALUES
('Milkshake', 'Chocolate, Morango ou Baunilha - 400ml', 12.90, 'products/milkshake.jpg', 4, 1, true),
('Sorvete', 'Casquinha com uma bola de sorvete', 7.90, 'products/ice-cream.jpg', 4, 2, true),
('Sundae', 'Sorvete com calda de chocolate ou morango', 9.90, 'products/sundae.jpg', 4, 3, true),
('Brownie', 'Brownie de chocolate com sorvete', 13.90, 'products/brownie.jpg', 4, 4, true),
('Torta de Limão', 'Fatia de torta de limão', 10.90, 'products/lemon-pie.jpg', 4, 5, true); 