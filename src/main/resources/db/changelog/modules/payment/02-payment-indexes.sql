--liquibase formatted sql

--changeset payment:02-payment-indexes

-- Índices para tabela de pagamentos
CREATE INDEX IF NOT EXISTS idx_payment_amount ON payments(amount);
CREATE INDEX IF NOT EXISTS idx_payment_type ON payments(type);
CREATE INDEX IF NOT EXISTS idx_payment_user ON payments(user_id);
CREATE INDEX IF NOT EXISTS idx_payment_tid ON payments (tid);