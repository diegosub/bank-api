package br.com.bank.api.auth.jwt.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.bank.api.auth.jwt.service.JwtUserDetailsService;
import br.com.bank.api.auth.jwt.token.JwtToken;
import br.com.bank.api.auth.jwt.user.JwtUser;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUserDetailsService service;

	@Autowired
	private JwtToken token;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String authToken = request.getHeader("Authorization");
		String login = token.obterLoginToken(authToken);

		if (login != null) {
			if (token.validarToken(authToken, login)) {
				JwtUser user = this.service.loadUserByUsername(login);
				
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				logger.info("O usuário " + login + " foi autenticado.");
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			}
		}

		chain.doFilter(request, response);
	}
	
}
