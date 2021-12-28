package br.com.bank.api.dto.transferencia;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferenciaDto {

    @NotNull(message = "A conta de origem é obrigatória.")
    private Long contaId;

    @NotNull(message = "A conta de destino é obrigatória.")
    private Long contaDestId;

    @NotNull(message = "O valor da transferência é obrigatório.")
    private Double valor;

}