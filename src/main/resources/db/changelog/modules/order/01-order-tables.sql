--liquibase formatted sql

--changeset order:01-order-tables runAlways:true
CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    customer_id INT,
    order_number VARCHAR(20) UNIQUE NOT NULL,
    status order_status_enum DEFAULT 'WAITING_PAYMENT' NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customers(id)
);

COMMENT ON TABLE orders IS 'Tabela que armazena os pedidos do sistema';
COMMENT ON COLUMN orders.id IS 'Identificador único do pedido';
COMMENT ON COLUMN orders.customer_id IS 'Referência ao cliente que fez o pedido';
COMMENT ON COLUMN orders.order_number IS 'Número único identificador do pedido para negócio';
COMMENT ON COLUMN orders.status IS 'Status atual do pedido (usando tipo enumerado)';
COMMENT ON COLUMN orders.total IS 'Valor total do pedido em reais';
COMMENT ON COLUMN orders.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN orders.updated_at IS 'Data da última atualização do registro';

--changeset order:02-order-tables runAlways:true
CREATE TABLE IF NOT EXISTS order_items (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    observations TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES products(id)
);

COMMENT ON TABLE order_items IS 'Tabela que armazena os itens de cada pedido';
COMMENT ON COLUMN order_items.id IS 'Identificador único do item do pedido';
COMMENT ON COLUMN order_items.order_id IS 'Referência ao pedido associado';
COMMENT ON COLUMN order_items.product_id IS 'Referência ao produto vendido';
COMMENT ON COLUMN order_items.quantity IS 'Quantidade do produto no pedido';
COMMENT ON COLUMN order_items.unit_price IS 'Preço unitário do produto no momento da venda';
COMMENT ON COLUMN order_items.subtotal IS 'Subtotal do item (unit_price * quantity)';
COMMENT ON COLUMN order_items.observations IS 'Observações específicas sobre o item';
COMMENT ON COLUMN order_items.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN order_items.updated_at IS 'Data da última atualização do registro';

--changeset order:03-order-tables runAlways:true
CREATE TABLE IF NOT EXISTS order_payments (
    id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    payment_id INTEGER NOT NULL,
    tid VARCHAR(255),
    status payment_status_enum DEFAULT 'PENDING' NOT NULL,
    amount INTEGER NOT NULL,
    paid_at TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_order_payment_order FOREIGN KEY (order_id) REFERENCES orders(id),
    CONSTRAINT fk_order_payment_payment FOREIGN KEY (payment_id) REFERENCES payments(id)
);

COMMENT ON TABLE order_payments IS 'Tabela que armazena os pagamentos associados a pedidos';
COMMENT ON COLUMN order_payments.id IS 'Identificador único do pagamento do pedido';
COMMENT ON COLUMN order_payments.order_id IS 'Referência ao pedido associado';
COMMENT ON COLUMN order_payments.payment_id IS 'Referência ao método de pagamento utilizado';
COMMENT ON COLUMN order_payments.tid IS 'Identificador da transação no gateway de pagamento';
COMMENT ON COLUMN order_payments.status IS 'Status do pagamento';
COMMENT ON COLUMN order_payments.amount IS 'Valor do pagamento em centavos (para precisão)';
COMMENT ON COLUMN order_payments.paid_at IS 'Data e hora em que o pagamento foi confirmado';
COMMENT ON COLUMN order_payments.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN order_payments.updated_at IS 'Data da última atualização do registro';