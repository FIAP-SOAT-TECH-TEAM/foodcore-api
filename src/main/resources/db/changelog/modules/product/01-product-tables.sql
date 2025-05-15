--liquibase formatted sql

--changeset product:01-product-tables

-- Tabela de Categorias
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    image_url VARCHAR(500),
    display_order INT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

COMMENT ON TABLE categories IS 'Tabela que armazena as categorias de produtos';
COMMENT ON COLUMN categories.id IS 'Identificador único da categoria';
COMMENT ON COLUMN categories.name IS 'Nome da categoria';
COMMENT ON COLUMN categories.description IS 'Descrição da categoria';
COMMENT ON COLUMN categories.image_url IS 'URL da imagem da categoria';
COMMENT ON COLUMN categories.display_order IS 'Ordem de exibição da categoria';
COMMENT ON COLUMN categories.active IS 'Indica se a categoria está ativa ou não';
COMMENT ON COLUMN categories.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN categories.updated_at IS 'Data da última atualização do registro';

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(500),
    display_order INT,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES categories(id)
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
COMMENT ON COLUMN products.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN products.updated_at IS 'Data da última atualização do registro';

-- Tabela de estoque
CREATE TABLE IF NOT EXISTS stock (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_stock_product FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT chk_quantity_positive CHECK (quantity > 0)
);

COMMENT ON TABLE stock IS 'Tabela que armazena o estoque dos produtos';
COMMENT ON COLUMN stock.id IS 'Identificador único do registro de estoque';
COMMENT ON COLUMN stock.product_id IS 'Referência ao produto em estoque';
COMMENT ON COLUMN stock.quantity IS 'Quantidade disponível em estoque (deve ser maior que zero)';
COMMENT ON COLUMN stock.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN stock.updated_at IS 'Data da última atualização do registro';
COMMENT ON CONSTRAINT chk_quantity_positive ON stock IS 'Garante que a quantidade em estoque seja sempre positiva';