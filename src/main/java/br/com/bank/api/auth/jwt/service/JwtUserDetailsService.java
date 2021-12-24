package br.com.bank.api.auth.jwt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.bank.api.auth.jwt.factory.JwtUserFactory;
import br.com.bank.api.auth.jwt.user.JwtUser;
import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.domain.business.seguranca.repository.UsuarioRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

   @Autowired
   UsuarioRepository usuarioRepository;

   @Override
   public JwtUser loadUserByUsername(String cpf) {
      Usuario usuario = usuarioRepository.pesquisarPorCpf(Long.parseLong(cpf));
      if (usuario == null) {
         throw new UsernameNotFoundException("Nenhum usuário encontrado com o cpf:  " + cpf);
      } else {        
         return JwtUserFactory.create(usuario);
      }
   }
}
