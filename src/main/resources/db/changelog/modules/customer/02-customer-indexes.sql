--liquibase formatted sql

--changeset customer:indexes-001
CREATE INDEX idx_customer_document ON customers(document);
CREATE INDEX idx_customer_email ON customers(email);
CREATE INDEX idx_customer_active ON customers(active); 