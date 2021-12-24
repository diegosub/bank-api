package br.com.bank.domain.business.seguranca.repository;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.domain.generic.repository.GenericRepository;

@Lazy(true)
public interface UsuarioRepository extends GenericRepository<Usuario, Long> {

   @Query("SELECT u FROM Usuario u WHERE u.cpf = :cpf")
   Usuario pesquisarPorCpf(@Param("cpf") Long cpf);

   @Query("SELECT count(u) FROM Usuario u WHERE u.cpf = :cpf")
   Integer quantidadePorCpf(@Param("cpf") Long cpf);
      
}