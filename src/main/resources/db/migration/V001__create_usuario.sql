CREATE SCHEMA seguranca
    AUTHORIZATION postgres;
    
    
CREATE SEQUENCE seguranca.sq_usuario;

ALTER SEQUENCE seguranca.sq_usuario
    OWNER TO postgres;
    
    
CREATE TABLE seguranca.tb_usuario
(
    id numeric NOT NULL,
    nome character varying(200) NOT NULL,
    cpf numeric(11) NOT NULL,
    senha character varying(500) NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id),
    CONSTRAINT uk_usuario_cpf UNIQUE (cpf)
);

ALTER TABLE IF EXISTS seguranca.tb_usuario
    OWNER to postgres;