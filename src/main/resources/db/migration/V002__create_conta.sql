CREATE SCHEMA operacao
    AUTHORIZATION postgres;
    
    
CREATE SEQUENCE operacao.sq_conta;

ALTER SEQUENCE operacao.sq_conta
    OWNER TO postgres;
    
 
CREATE TABLE operacao.tb_conta
(
    id numeric NOT NULL,
    usuario_id numeric NOT NULL,
    saldo numeric(10, 2) NOT NULL default 0,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone,
    CONSTRAINT pk_conta PRIMARY KEY (id),
    CONSTRAINT uk_conta_usuario_id UNIQUE (usuario_id),
    CONSTRAINT fk_conta_usuario FOREIGN KEY (usuario_id)
        REFERENCES seguranca.tb_usuario (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
);

ALTER TABLE IF EXISTS operacao.tb_conta
    OWNER to postgres;