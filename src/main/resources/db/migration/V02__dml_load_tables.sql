-- Define o schema de trabalho
SET search_path TO ms_pagamento;

-- Inserindo pagamentos
INSERT INTO pagamento (id, pedido_id, forma_pagamento, numero_cartao_credito, valor, data_criacao, data_ultima_alteracao)
VALUES
    (uuid_generate_v4(), uuid_generate_v4(), 1, '4111111111111111', 150.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 2, NULL, 89.90, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 1, '5500000000000004', 299.99, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 3, NULL, 59.50, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 1, '4111111111111111', 399.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 2, NULL, 120.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 1, '6011000000000004', 200.25, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 3, NULL, 75.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 1, '3530111333300000', 180.40, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
    (uuid_generate_v4(), uuid_generate_v4(), 2, NULL, 50.00, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
