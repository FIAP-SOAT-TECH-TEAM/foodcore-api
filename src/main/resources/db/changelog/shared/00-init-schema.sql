--liquibase formatted sql

--changeset shared:types-001 context:local,dev,prod
-- Tipos enumerados (necessários em todos os ambientes)
-- Enum para tipo de pagamento
CREATE TYPE payment_type_enum AS ENUM ('CREDIT_CARD', 'DEBIT_CARD', 'PIX', 'CASH');

-- Enum para status de pagamento
CREATE TYPE payment_status_enum AS ENUM ('PENDING', 'APPROVED', 'REJECTED', 'REFUNDED');

-- Enum para status de pedido
CREATE TYPE order_status_enum AS ENUM ('RECEIVED', 'WAITING_PAYMENT', 'PREPARING', 'READY', 'COMPLETED', 'CANCELLED'); 