package br.com.bank.domain.business.operacao.enums;

import lombok.Getter;

@Getter
public enum TipoOperacaoEnum {
    DEPOSITO("D"),
    TRANSFERENCIA("T");
    private String tipo;

    TipoOperacaoEnum(String tipo) {
        this.tipo = tipo;
    }
}
