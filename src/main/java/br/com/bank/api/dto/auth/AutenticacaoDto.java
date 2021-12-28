package br.com.bank.api.dto.auth;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AutenticacaoDto {

   @NotBlank(message = "O login é obrigatório.")
   private String cpf;

   @NotBlank(message = "A senha é obrigatória.")
   private String senha;

}
