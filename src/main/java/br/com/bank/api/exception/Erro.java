package br.com.bank.api.exception;

import java.util.Date;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class Erro
{
   private Integer status;
   
   private Date timestamp;
   
   private String type;
   
   private String title;
   
   private String detail;
   
   private String userMessage;
   
   private List<ErroObject> objects;
}
