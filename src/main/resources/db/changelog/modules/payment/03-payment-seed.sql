--liquibase formatted sql

--changeset payment:03-payment-seed context:local,dev runAlways:true onError:MARK_RAN
-- Pagamentos para ambiente de desenvolvimento

-- Pagamento 1 (Cartão de Crédito) - João Silva
INSERT INTO payments (customer_id, type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    c.id,
    'CREDIT_CARD',
    NOW() + interval '30 days',
    150.00,
    NULL,
    'Pagamento aprovado via cartão',
    NOW(),
    NOW()
FROM customers c
WHERE c.name = 'João Silva' AND c.email = 'joao@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.customer_id = c.id AND p.type = 'CREDIT_CARD'
  );

-- Pagamento 2 (Débito) - Maria Oliveira
INSERT INTO payments (customer_id, type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    c.id,
    'DEBIT_CARD',
    NOW() + interval '1 hour',
    89.90,
    NULL,
    'Pagamento via débito automático',
    NOW(),
    NOW()
FROM customers c
WHERE c.name = 'Maria Oliveira' AND c.email = 'maria@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.customer_id = c.id AND p.type = 'DEBIT_CARD'
  );

-- Pagamento 3 (PIX) - sem cliente
INSERT INTO payments (type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    120.00,
    'https://pix.example.com/qr/123456',
    'QR Code gerado para pagamento',
    NOW(),
    NOW()
WHERE NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.customer_id IS NULL AND p.type = 'PIX'
);

-- Pagamento 4 (Dinheiro) - Ana Souza
INSERT INTO payments (customer_id, type, expires_in, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    c.id,
    'CASH',
    NOW() + interval '24 hour',
    45.50,
    NULL,
    'Pagamento em dinheiro na entrega',
    NOW(),
    NOW()
FROM customers c
WHERE c.name = 'Ana Souza' AND c.email = 'ana@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.customer_id = c.id AND p.type = 'CASH'
  );