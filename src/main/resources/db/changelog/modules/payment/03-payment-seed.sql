--liquibase formatted sql

--changeset payment:03-payment-seed context:local,dev runAlways:true onError:MARK_RAN
-- Pagamentos para ambiente de desenvolvimento

-- Pagamento 1 (Cartão de Crédito) - João Silva
INSERT INTO payments (
    user_id, order_id, type, expires_in, status, paid_at, tid, amount, qr_code, observations, created_at, updated_at
)
SELECT
    u.id,
    (SELECT id FROM orders WHERE order_number = 'ORD-2025-00000001'),
    'CREDIT_CARD',
    NOW() + interval '30 days',
    'APPROVED',
    NOW() - interval '1 hour',
    '104ab379-hc37-4e2e-a2f5-c379f2340e56',
    32.80,
    '00020101021243650016COM.MERCADOLIBRE02013063682409123-aaaa-bbbb-cccc-1234567890ab5204000053039865802BR5908Joao Test6009CURITIBA62070503***63AS04A13B',
    'Pagamento aprovado via cartão',
    NOW() - interval '2 hour',
    NOW() - interval '1 hour'
FROM users u
WHERE u.email = 'joao@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.tid = '104ab379-hc37-4e2e-a2f5-c379f2340e56'
  );

-- Pagamento 2 (Débito) - Maria Oliveira
INSERT INTO payments (
    user_id, order_id, type, expires_in, status, paid_at, tid, amount, qr_code, observations, created_at, updated_at
)
SELECT
    u.id,
    (SELECT id FROM orders WHERE order_number = 'ORD-2025-00000002'),
    'DEBIT_CARD',
    NOW() + interval '1 hour',
    'APPROVED',
    NOW() - interval '25 minute',
    '1044b379-hc37-4e2e-a2f5-c379f2340e59',
    79.70,
    '000201010asdasd43650016COM.MERCADOLIBRE02013063682409123-aaaa-bbbb-cccc-1234567890ab5204000053039865802BR5908Joao Test6009CURITIBA62070503***63AS04A13B',
    'Pagamento via débito automático',
    NOW() - interval '30 minute',
    NOW() - interval '25 minute'
FROM users u
WHERE u.email = 'maria@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.tid = '1044b379-hc37-4e2e-a2f5-c379f2340e59'
  );

-- Pagamento 3 (PIX) - Maria Oliveira
INSERT INTO payments (
    user_id, order_id, type, expires_in, status, paid_at, tid, amount, qr_code, observations, created_at, updated_at
)
SELECT
    u.id,
    (SELECT id FROM orders WHERE order_number = 'ORD-2025-00000003'),
    'PIX',
    NOW() + interval '1 hour',
    'PENDING',
    NULL,
    '1044b279-hc37-4e2e-a2f5-c379f2340e53',
    19.90,
    '00020101021243650016COM.MERCADOLIBRE02013063682409123-aaaa-bbbb-cccc-1234567890ab5204000053039865802BR5908Joao Test6009CURITIBA62070503***6304A13B',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
FROM users u
WHERE u.email = 'maria@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.tid = '1044b279-hc37-4e2e-a2f5-c379f2340e53'
  );

-- Pagamento 4 (PIX) - Maria Oliveira
INSERT INTO payments (
    user_id, order_id, type, expires_in, status, paid_at, tid, amount, qr_code, observations, created_at, updated_at
)
SELECT
    u.id,
    (SELECT id FROM orders WHERE order_number = 'ORD-2025-00000004'),
    'PIX',
    NOW() + interval '1 hour',
    'PENDING',
    NULL,
    '10448279-hc37-4e2e-a2f5-c379f2340e53',
    4.90,
    '00020101021243650016COM.MERCADOLIBRE02013063682409cafe-9876-abcd-1234-1234567890cd5204000053039865802BR5912Maria Silva6009RIO BRANCO62070503***6304C7D2',
    'QR Code gerado para pagamento',
    NOW() - interval '5 minute',
    NOW() - interval '5 minute'
FROM users u
WHERE u.email = 'maria@email.com'
  AND NOT EXISTS (
      SELECT 1 FROM payments p WHERE p.tid = '10448279-hc37-4e2e-a2f5-c379f2340e53'
  );