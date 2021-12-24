package br.com.bank.api.dto.auth;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsuarioAutenticadoDto {
   
   private Long id;
   private String token;

}
