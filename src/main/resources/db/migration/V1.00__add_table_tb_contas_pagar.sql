CREATE TABLE IF NOT EXISTS tb_contas_pagar (
    id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    data_vencimento date,
    data_pagamento date,
    valor decimal(10, 2),
    situacao varchar(20),
    PRIMARY KEY(id)
);