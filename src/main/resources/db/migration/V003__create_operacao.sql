CREATE SEQUENCE operacao.sq_operacao;

ALTER SEQUENCE operacao.sq_operacao
    OWNER TO postgres;

CREATE TABLE operacao.tb_operacao
(
    id numeric NOT NULL,
    usuario_id numeric NOT NULL,
    tipo character varying(1) NOT NULL,  -- T (transferencia) ou D (deposito)
    descricao character varying(200), 
    valor numeric(10,2) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    CONSTRAINT pk_operacao PRIMARY KEY (id),
    CONSTRAINT fk_operacao_usuario FOREIGN KEY (usuario_id)
        REFERENCES seguranca.tb_usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS operacao.tb_operacao
    OWNER to postgres;