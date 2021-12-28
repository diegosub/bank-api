package br.com.bank.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.bank.api.auth.jwt.filter.JwtAuthenticationFilter;
import br.com.bank.api.auth.jwt.filter.JwtUnauthorized;
import br.com.bank.api.auth.jwt.service.JwtUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private JwtUnauthorized unauthorized;

   @Autowired
   private JwtUserDetailsService userDetailsService;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorized).and().sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
               .antMatchers(HttpMethod.GET, "/", "/*.html", "/favicon.ico", "/**/*.html", "/**/*.css", "/**/*.js")
               .permitAll().antMatchers(AUTH_WHITELIST)
               .permitAll().anyRequest().authenticated();
      http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
      http.headers().cacheControl();
      http.cors();
   }

   @Autowired
   public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
      authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder());
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public JwtAuthenticationFilter authenticationTokenFilterBean() throws Exception {
      return new JwtAuthenticationFilter();
   }

   @Bean
   public AuthenticationManager customAuthenticationManager() throws Exception {
      return authenticationManager();
   }

   /**
    * Permit access swagger in localhost
    */
   private static final String[] AUTH_WHITELIST = {
           "/swagger-resources/**",
           "/swagger-ui.html",
           "/v2/api-docs",
           "/webjars/**",
           "/auth",
           "/conta",
           "/conta/*"
   };

}
