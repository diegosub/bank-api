package br.com.bank.api.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class ErroObject
{
   private String name;
   
   private String userMessage;
}
