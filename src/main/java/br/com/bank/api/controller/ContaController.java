package br.com.bank.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bank.api.dto.conta.ContaDto;
import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.business.operacao.service.ContaService;
import br.com.bank.domain.business.seguranca.model.Usuario;

@RestController
@RequestMapping("conta")
@CrossOrigin(origins = "*")
public class ContaController extends BaseController {

   @Autowired
   ContaService service;

   @GetMapping(value = "{codigo}")
   public ResponseEntity<Conta> get(HttpServletRequest request, @PathVariable("codigo") Long codigo) {
      return ResponseEntity.ok(service.getById(codigo));
   }

   @GetMapping
   public ResponseEntity<List<Conta>> pesquisar(Conta filtro) {
      return ResponseEntity.ok(service.pesquisar(filtro));
   }

   @PostMapping
   public ResponseEntity<Conta> inserir(HttpServletRequest request, @Valid @RequestBody ContaDto dto) {
      Conta entity = this.convertDtoToModel(dto);
      return ResponseEntity.ok(service.inserir(entity));
   }

   /**
    * Metodo default converter dto to model
    * @param dto
    * @return model
    */
   private Conta convertDtoToModel(ContaDto dto) {      
      Conta conta = new Conta();
      conta.setUsuario(new Usuario());
      conta.getUsuario().setNome(dto.getNome());
      conta.getUsuario().setCpf(Long.parseLong(dto.getCpf()));
      return conta;      
   }

}