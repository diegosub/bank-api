package br.com.bank.domain.business.operacao.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.bank.domain.business.operacao.model.Movimentacao;
import br.com.bank.domain.business.operacao.repository.MovimentacaoRepository;
import br.com.bank.domain.generic.service.CrudService;
import br.com.bank.domain.generic.specification.GenericSpecification;

@Service
public class MovimentacaoService extends CrudService<Movimentacao, Long, MovimentacaoRepository> {

   private static final long serialVersionUID = 1L;
   
   public List<Movimentacao> pesquisar(Long contaId, Pageable pageable) {
      Page<Movimentacao> lista = repository.findAll(Specification.where(GenericSpecification.<Movimentacao>igual("contaId", contaId))
                                                                   .and(GenericSpecification.<Movimentacao>order(new String[] {"createdAt"})),
                                                                   PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));

      return lista.getContent();
   }
}