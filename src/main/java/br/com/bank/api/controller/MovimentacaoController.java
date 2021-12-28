package br.com.bank.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bank.domain.business.operacao.model.Movimentacao;
import br.com.bank.domain.business.operacao.service.MovimentacaoService;

@RestController
@RequestMapping("movimentacao")
@CrossOrigin(origins = "*")
public class MovimentacaoController {

   @Autowired
   MovimentacaoService service;

   @GetMapping(value = "{contaId}")
   public ResponseEntity<List<Movimentacao>> get(HttpServletRequest request, @PathVariable("contaId") Long contaId, @PageableDefault Pageable pageable) {
      return ResponseEntity.ok(service.pesquisar(contaId, pageable));
   }

}