package br.com.bank.api.dto.conta;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaDto
{
   @NotBlank(message = "O nome é obrigatório.")
   private String nome;
   
   @NotBlank(message = "O cpf é obrigatório.")
   @CPF(message = "O cpf deve ser válido.")
   private String cpf;
}
