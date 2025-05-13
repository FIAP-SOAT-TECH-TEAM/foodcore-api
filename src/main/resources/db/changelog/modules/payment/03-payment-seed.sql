--liquibase formatted sql

--changeset payment:03-payment-seed context:local,dev runAlways:true onError:MARK_RAN
-- Pagamentos para ambiente de desenvolvimento

-- Pagamento 1 (Cartão de Crédito) - João Silva
INSERT INTO payments (customer_id, type, expires_in, tid, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    c.id,
    'CREDIT_CARD',
    NOW() + interval '30 days',
    'TID-000001',
    32.80,
    NULL,
    'Pagamento aprovado via cartão',
    NOW() - interval '2 hour',
    NOW() - interval '1 hour'
FROM customers c
WHERE c.email = 'joao@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.tid = 'TID-000001'
  );

-- Pagamento 2 (Débito) - Maria Oliveira
INSERT INTO payments (customer_id, type, expires_in, tid, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    c.id,
    'DEBIT_CARD',
    NOW() + interval '1 hour',
    'TID-000002',
    79.70,
    NULL,
    'Pagamento via débito automático',
    NOW() - interval '30 minute',
    NOW() - interval '25 minute'
FROM customers c
WHERE c.email = 'maria@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.tid = 'TID-000002'
  );

-- Pagamento 3 (PIX) - sem cliente
INSERT INTO payments (type, expires_in, tid, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    'TID-000003',
    19.90,
    'https://pix.example.com/qr/123456',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.tid = 'TID-000003'
);

-- Pagamento 4 (PIX) - sem cliente
INSERT INTO payments (type, expires_in, tid, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    'TID-000004',
    4.90,
    'https://pix.example.com/qr/123457',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.tid = 'TID-000004'
);

-- Pagamento 5 (PIX) - sem cliente
INSERT INTO payments (type, expires_in, tid, amount, qr_code_url, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    'TID-000005',
    4.90,
    'https://pix.example.com/qr/123457',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.tid = 'TID-000005'
);