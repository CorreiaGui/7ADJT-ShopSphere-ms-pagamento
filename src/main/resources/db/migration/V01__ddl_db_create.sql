-- Criação do schema
CREATE SCHEMA IF NOT EXISTS ms_pagamento;

-- Garante que tudo será criado dentro do schema ms-pagamento
SET search_path TO ms_pagamento;

-- Criação da extensão para UUID
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Tabela de pagamentos
CREATE TABLE pagamento (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    pedido_id UUID NOT NULL,
    forma_pagamento INTEGER NOT NULL,
    numero_cartao_credito VARCHAR(30) NULL,
    valor NUMERIC(10,2) NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_ultima_alteracao TIMESTAMP
);

-- Índice adicional no  campo "pedido" para facilitar buscas do pagamento
CREATE INDEX idx_pagamento_pedido ON pagamento (pedido_id);
