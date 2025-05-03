--liquibase formatted sql

--changeset product:01-product-tables

-- Tabela de Categorias
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    image_url VARCHAR(500),
    display_order INT,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

COMMENT ON TABLE categories IS 'Tabela que armazena as categorias de produtos';
COMMENT ON COLUMN categories.id IS 'Identificador único da categoria';
COMMENT ON COLUMN categories.name IS 'Nome da categoria';
COMMENT ON COLUMN categories.description IS 'Descrição da categoria';
COMMENT ON COLUMN categories.image_url IS 'URL da imagem da categoria';
COMMENT ON COLUMN categories.display_order IS 'Ordem de exibição da categoria';
COMMENT ON COLUMN categories.active IS 'Indica se a categoria está ativa ou não';

-- Tabela de Produtos
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000),
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500),
    category_id BIGINT NOT NULL REFERENCES categories(id),
    display_order INT,
    active BOOLEAN NOT NULL DEFAULT TRUE
);

COMMENT ON TABLE products IS 'Tabela que armazena os produtos disponíveis para venda';
COMMENT ON COLUMN products.id IS 'Identificador único do produto';
COMMENT ON COLUMN products.name IS 'Nome do produto';
COMMENT ON COLUMN products.description IS 'Descrição do produto';
COMMENT ON COLUMN products.price IS 'Preço do produto';
COMMENT ON COLUMN products.image_url IS 'URL da imagem do produto';
COMMENT ON COLUMN products.category_id IS 'Referência para a categoria do produto';
COMMENT ON COLUMN products.display_order IS 'Ordem de exibição do produto';
COMMENT ON COLUMN products.active IS 'Indica se o produto está ativo ou não'; 