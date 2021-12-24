package br.com.bank.domain.business.seguranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.bank.api.auth.jwt.token.JwtToken;
import br.com.bank.api.auth.jwt.user.JwtUser;
import br.com.bank.api.dto.auth.AutenticacaoDto;
import br.com.bank.api.dto.auth.UsuarioAutenticadoDto;

@Service
public class LoginService {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtToken jwtTokenUtil;

    public UsuarioAutenticadoDto login(AutenticacaoDto dto) {
        Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));
        JwtUser user = (JwtUser) auth.getPrincipal();

        final String token = jwtTokenUtil.gerarToken(user.getId(), user.getUsername());
        UsuarioAutenticadoDto retorno = new UsuarioAutenticadoDto();
        retorno.setId(((JwtUser)auth.getPrincipal()).getId());
        retorno.setToken(token);
        return retorno;        
    }    
}
