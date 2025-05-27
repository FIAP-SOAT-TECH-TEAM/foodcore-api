--liquibase formatted sql

--changeset payment:01-payment-tables runAlways:true
CREATE TABLE IF NOT EXISTS payments (
    id SERIAL PRIMARY KEY,
    user_id INT,
    type payment_type_enum NOT NULL,
    expires_in TIMESTAMP NOT NULL,
    tid VARCHAR(255) UNIQUE NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    qr_code VARCHAR(255),
    observations TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_payment_user FOREIGN KEY (user_id) REFERENCES users(id)
);

COMMENT ON TABLE payments IS 'Tabela que armazena os pagamentos do sistema';
COMMENT ON COLUMN payments.id IS 'Identificador único do pagamento';
COMMENT ON COLUMN payments.user_id IS 'Referência ao usuário associado ao pagamento';
COMMENT ON COLUMN payments.type IS 'Tipo de pagamento';
COMMENT ON COLUMN payments.expires_in IS 'Data e hora de expiração do pagamento';
COMMENT ON COLUMN payments.tid IS 'Identificador da transação no gateway de pagamento';
COMMENT ON COLUMN payments.amount IS 'Valor total do pagamento em reais';
COMMENT ON COLUMN payments.qr_code IS 'Conteúdo do QR Code para pagamentos (quando aplicável)';
COMMENT ON COLUMN payments.observations IS 'Observações adicionais sobre o pagamento';
COMMENT ON COLUMN payments.created_at IS 'Data de criação do registro';
COMMENT ON COLUMN payments.updated_at IS 'Data da última atualização do registro';