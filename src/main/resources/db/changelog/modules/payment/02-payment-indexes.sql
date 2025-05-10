--liquibase formatted sql

--changeset payment:02-payment-indexes
CREATE INDEX idx_payment_status ON payments(status);
CREATE INDEX idx_payment_type ON payments(type);
CREATE INDEX idx_payment_order ON payments(order_id); 