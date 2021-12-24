package br.com.bank.domain.business.operacao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.business.operacao.repository.ContaRepository;
import br.com.bank.domain.business.seguranca.model.Usuario;
import br.com.bank.domain.business.seguranca.service.UsuarioService;
import br.com.bank.domain.exception.NegocioException;
import br.com.bank.domain.generic.service.CrudService;
import br.com.bank.domain.generic.specification.GenericSpecification;

@Service
public class ContaService extends CrudService<Conta, Long, ContaRepository> {

   private static final long serialVersionUID = 1L;
   
   @Autowired
   private UsuarioService usuarioService;
   
   public List<Conta> pesquisar(Conta conta) {
      List<Conta> lista = repository.findAll(Specification.where(GenericSpecification.<Conta>fetch(new String[] {"usuario"}))
                                                            .and(GenericSpecification.<Conta>order(new String[] {"id"})));

      return lista;
   }
   
   @Override
   public Conta getById(Long id)
   {
      Conta retorno = repository.findOne(Specification.where(GenericSpecification.<Conta>fetch(new String[] {"usuario"}))
                                                        .and(GenericSpecification.<Conta>igual("id", id)))
                                                        .orElseThrow(() -> new NegocioException("Conta não encontrada."));
      return retorno;
   }
   
   @Override
   @Transactional
   public Conta inserir(Conta entity)
   {
      Usuario usuario = entity.getUsuario();
      usuario = usuarioService.inserir(entity.getUsuario());
      entity.setUsuarioId(usuario.getId());
      entity.setSaldo(0.0);
      repository.save(entity);
      return entity;
   }
}