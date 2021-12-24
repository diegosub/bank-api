package br.com.bank.api.auth.jwt.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
public class JwtUser implements UserDetails {

   private static final long serialVersionUID = 1L;

   private final Long id;

   private final String username;

   private final String password;

   private final Collection<? extends GrantedAuthority> authorities;

   public JwtUser(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.username = username;
      this.password = password;
      this.authorities = authorities;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }

}
