package br.com.bank.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bank.api.dto.auth.AutenticacaoDto;
import br.com.bank.api.dto.auth.UsuarioAutenticadoDto;
import br.com.bank.domain.business.seguranca.service.LoginService;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {
   
   @Autowired
   private LoginService loginService;

   @PostMapping(value = "/auth")
   public ResponseEntity<UsuarioAutenticadoDto> logar(@RequestBody @Valid AutenticacaoDto dto) {
      return ResponseEntity.ok(loginService.login(dto));
   }

}
