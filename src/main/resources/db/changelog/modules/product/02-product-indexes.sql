--liquibase formatted sql

--changeset product:indexes-001
CREATE INDEX idx_product_category ON products(category_id);
CREATE INDEX idx_product_active ON products(active);
CREATE INDEX idx_category_active ON categories(active); 