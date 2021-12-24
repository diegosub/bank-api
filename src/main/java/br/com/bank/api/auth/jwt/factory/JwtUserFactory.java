package br.com.bank.api.auth.jwt.factory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import br.com.bank.api.auth.jwt.user.JwtUser;
import br.com.bank.domain.business.seguranca.model.Usuario;

public class JwtUserFactory {
   private JwtUserFactory() {
   }

   public static JwtUser create(Usuario usuario) {
      return new JwtUser(usuario.getId(), usuario.getCpf().toString(), usuario.getSenha(), mapToGranteAuthorities("ADM"));
   }

   private static List<GrantedAuthority> mapToGranteAuthorities(String perfil) {
      List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
      authorities.add(new SimpleGrantedAuthority(perfil));
      return authorities;
   }
}
