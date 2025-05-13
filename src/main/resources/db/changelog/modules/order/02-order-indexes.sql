--liquibase formatted sql

--changeset order:01-order-indexes runAlways:true

-- Índices para tabela de pedidos
CREATE INDEX IF NOT EXISTS idx_orders_customer_id ON orders (customer_id);
CREATE INDEX IF NOT EXISTS idx_orders_status ON orders (status);
CREATE INDEX IF NOT EXISTS idx_orders_order_number ON orders (order_number);

-- Índices para tabela de itens do pedido
CREATE INDEX IF NOT EXISTS idx_order_items_order_id ON order_items (order_id);
CREATE INDEX IF NOT EXISTS idx_order_items_product_id ON order_items (product_id);

-- Índices para tabela de pagamentos do pedido
CREATE INDEX IF NOT EXISTS idx_order_payments_order_id ON order_payments (order_id);
CREATE INDEX IF NOT EXISTS idx_order_payments_payment_id ON order_payments (payment_id);
CREATE INDEX IF NOT EXISTS idx_order_payments_status ON order_payments (status);
CREATE INDEX IF NOT EXISTS idx_order_payments_tid ON order_payments (tid);
CREATE INDEX IF NOT EXISTS idx_order_payments_paid_at ON order_payments (paid_at) WHERE paid_at IS NOT NULL;