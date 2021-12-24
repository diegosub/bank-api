package br.com.bank.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.bank.api.auth.jwt.token.JwtToken;

public class BaseController {
   
   @Autowired
   protected JwtToken token;

   @Autowired
   protected ModelMapper mapper;

   public void convertModel(Object o1, Object o2) {
      mapper.map(o1, o2);
   }

   public Long obterUsuarioFromToken(HttpServletRequest request) {
      String token = request.getHeader("Authorization");
      return this.token.obterCodigoToken(token);
   }

   protected String[] juncoesGet() {
      return null;
   }

   protected String[] juncoesPesquisar() {
      return null;
   }

   protected String[] ordenacoesGet() {
      return null;
   }

   protected String[] ordenacoesPesquisar() {
      return null;
   }
}
