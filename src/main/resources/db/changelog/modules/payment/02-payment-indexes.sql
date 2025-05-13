--liquibase formatted sql

--changeset payment:02-payment-indexes
CREATE INDEX IF NOT EXISTS idx_payment_status ON payments(status);
CREATE INDEX IF NOT EXISTS idx_payment_type ON payments(type);
CREATE INDEX IF NOT EXISTS idx_payment_order ON payments(order_id);