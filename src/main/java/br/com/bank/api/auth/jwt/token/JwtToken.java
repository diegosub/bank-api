package br.com.bank.api.auth.jwt.token;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtToken implements Serializable {

   private static final long serialVersionUID = 1L;

   static final String CLAIM_KEY_ID_USR = "sub";

   static final String CLAIM_KEY_LOGIN = "name";

   static final String CLAIM_KEY_CREATED = "created";

   static final String CLAIM_KEY_EXPIRED = "exp";

   @Value("${jwt.secret}")
   private String secret;

   @Value("${jwt.expiration}")
   private Long expiration;

   public String gerarToken(Long codigo, String login) {
      Map<String, Object> claims = new HashMap<>();

      final Date createDate = new Date();
      claims.put(CLAIM_KEY_CREATED, createDate);
      claims.put(CLAIM_KEY_LOGIN, login);
      claims.put(CLAIM_KEY_ID_USR, codigo);

      final Date createdDate = (Date) claims.get(CLAIM_KEY_CREATED);
      final Date expirationDate = new Date(createdDate.getTime() + expiration);
      return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();

   }

   public Boolean validarToken(String token, String login) {
      final String loginToken = this.obterLoginToken(token);
      return (loginToken.equals(login) && !tokenExpirado(token));
   }

   public Long obterCodigoToken(String token) {
      Long codigo;

      try {
         final Claims claims = this.obterClaimsToken(token);
         codigo = claims.get(CLAIM_KEY_ID_USR, Long.class);
      } catch (Exception e) {
         codigo = null;
      }

      return codigo;
   }

   public String obterLoginToken(String token) {
      String login;

      try {
         final Claims claims = this.obterClaimsToken(token);
         login = claims.get(CLAIM_KEY_LOGIN, String.class);
      } catch (Exception e) {
         login = null;
      }

      return login;
   }

   public Date obterDataExpiracaoToken(String token) {
      Date expiration;

      try {
         final Claims claims = this.obterClaimsToken(token);
         expiration = claims.getExpiration();
      } catch (Exception e) {
         expiration = null;
      }

      return expiration;
   }

   private Claims obterClaimsToken(String token) {
      Claims claims;

      try {
         claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
      } catch (Exception e) {
         claims = null;
      }

      return claims;
   }

   private Boolean tokenExpirado(String token) {
      final Date expiration = obterDataExpiracaoToken(token);
      return expiration.before(new Date());
   }
}
