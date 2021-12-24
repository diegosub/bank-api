CREATE SEQUENCE operacao.sq_movimentacao;

ALTER SEQUENCE operacao.sq_movimentacao
    OWNER TO postgres;

CREATE TABLE operacao.tb_movimentacao
(
    id numeric NOT NULL,
    conta_id numeric NOT NULL,
    operacao_id numeric NOT NULL,
    valor numeric(10,2) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT pk_movimentacao PRIMARY KEY (id),
    CONSTRAINT fk_movimentacao_conta FOREIGN KEY (conta_id)
        REFERENCES operacao.tb_conta (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_movimentacao_operacao FOREIGN KEY (operacao_id)
        REFERENCES operacao.tb_operacao (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS operacao.tb_operacao
    OWNER to postgres;