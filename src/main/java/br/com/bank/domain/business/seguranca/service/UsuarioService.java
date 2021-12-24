package br.com.bank.domain.business.seguranca.service;

import java.util.List;

import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.domain.business.seguranca.repository.UsuarioRepository;
import br.com.bank.domain.exception.NegocioException;
import br.com.bank.domain.generic.service.CrudService;
import br.com.bank.domain.generic.specification.GenericSpecification;

@Service
public class UsuarioService extends CrudService<Usuario, Long, UsuarioRepository> {

   private static final long serialVersionUID = 1L;
   
   @Autowired
   private BCryptPasswordEncoder bcrypt;

   public List<Usuario> pesquisar(Usuario usuario, String[] juncoes, String[] ordenacoes) {
      List<Usuario> lista = repository.findAll(Specification.where(GenericSpecification.<Usuario>fetch(juncoes))
                                               .and(GenericSpecification.<Usuario>igual("cpf", usuario.getCpf()))
                                               .and(GenericSpecification.<Usuario>flike("nome", usuario.getNome()))
                                               .and(GenericSpecification.<Usuario>order(ordenacoes)));

      return lista;
   }

   public Usuario obter(Usuario usuario, String[] juncoes, String[] ordenacoes) {
      Usuario retorno = repository.findOne(Specification.where(GenericSpecification.<Usuario>fetch(juncoes))
                                                          .and(GenericSpecification.<Usuario>igual("id", usuario.getId()))
                                                          .and(GenericSpecification.<Usuario>order(ordenacoes)))
                                                          .orElseThrow(() -> new NegocioException("Usuário não encontrado."));
      return retorno;
   }

   @Override
   public void validarInserir(Usuario usuario) {
      Integer quantidade = repository.quantidadePorCpf(usuario.getCpf());

      if (quantidade != null && quantidade > 0) {
         throw new NegocioException("Já existe uma conta para este cpf.");
      }
   }
   
   @Override
   public void completarInserir(Usuario entity)
   {
      entity.setSenha(bcrypt.encode(this.generatePassword()));
   }

   public String generatePassword() {
      CharacterRule digits = new CharacterRule(EnglishCharacterData.Digit);
      PasswordGenerator passwordGenerator = new PasswordGenerator();
      return passwordGenerator.generatePassword(4, digits);
   }
}