--liquibase formatted sql

--changeset payment:03-payment-seed context:local,dev runAlways:true onError:MARK_RAN
-- Pagamentos para ambiente de desenvolvimento

-- Pagamento 1 (Cartão de Crédito) - João Silva
INSERT INTO payments (customer_id, type, expires_in, tid, amount, qr_code, observations, created_at, updated_at)
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
INSERT INTO payments (customer_id, type, expires_in, tid, amount, qr_code, observations, created_at, updated_at)
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
INSERT INTO payments (type, expires_in, tid, amount, qr_code, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    'TID-000003',
    19.90,
    '00020101021243650016COM.MERCADOLIBRE02013063682409123-aaaa-bbbb-cccc-1234567890ab5204000053039865802BR5908Joao Test6009CURITIBA62070503***6304A13B',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.tid = 'TID-000003'
);

-- Pagamento 4 (PIX) - sem cliente
INSERT INTO payments (type, expires_in, tid, amount, qr_code, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    'TID-000004',
    4.90,
    '00020101021243650016COM.MERCADOLIBRE02013063682409cafe-9876-abcd-1234-1234567890cd5204000053039865802BR5912Maria Silva6009RIO BRANCO62070503***6304C7D2',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.tid = 'TID-000004'
);

-- Pagamento 5 (PIX) - sem cliente
INSERT INTO payments (type, expires_in, tid, amount, qr_code, observations, created_at, updated_at)
SELECT
    'PIX',
    NOW() + interval '1 hour',
    'TID-000005',
    4.90,
    '00020101021243650016COM.MERCADOLIBRE02013063682409abcd-1234-efgh-5678-90abcdef12345204000053039865802BR5909Lucas Lima6009PORTO ALEGRE62070503***6304E2F1',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
WHERE NOT EXISTS (
    SELECT 1 FROM payments p WHERE p.tid = 'TID-000005'
);