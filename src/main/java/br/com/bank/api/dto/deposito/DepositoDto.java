package br.com.bank.api.dto.deposito;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DepositoDto {

    @NotNull(message = "A conta de depósito é obrigatória.")
    private Long contaId;

    @Max(value = 2000, message = "O valor máximo para depósito é de R$ 2.000,00")
    @NotNull(message = "O valor do depósito é obrigatório.")
    private Double valor;

}