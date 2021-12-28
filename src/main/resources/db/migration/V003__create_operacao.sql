CREATE SEQUENCE operacao.sq_operacao;

ALTER SEQUENCE operacao.sq_operacao
    OWNER TO postgres;

CREATE TABLE operacao.tb_operacao
(
    id numeric NOT NULL,
    conta_id numeric NOT NULL,
    conta_dest_id numeric,
    tipo character varying(1) NOT NULL,  -- T (transferencia) ou D (deposito)
    descricao character varying(200), 
    valor numeric(10,2) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT pk_operacao PRIMARY KEY (id),
    CONSTRAINT fk_operacao_conta FOREIGN KEY (conta_id)
        REFERENCES operacao.tb_conta (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT fk_operacao_conta_dest FOREIGN KEY (conta_dest_id)
            REFERENCES operacao.tb_conta (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
            NOT VALID
);

ALTER TABLE IF EXISTS operacao.tb_operacao
    OWNER to postgres;