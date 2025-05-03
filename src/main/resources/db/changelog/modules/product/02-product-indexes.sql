--liquibase formatted sql

--changeset product:02-product-indexes

-- Índices para tabela de categorias
CREATE INDEX idx_categories_name ON categories(name);
CREATE INDEX idx_categories_active ON categories(active);
CREATE INDEX idx_categories_display_order ON categories(display_order);

-- Índices para tabela de produtos
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_products_category_id ON products(category_id);
CREATE INDEX idx_products_active ON products(active);
CREATE INDEX idx_products_display_order ON products(display_order);
CREATE INDEX idx_products_price ON products(price); 