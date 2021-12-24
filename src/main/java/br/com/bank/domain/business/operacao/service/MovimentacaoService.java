package br.com.bank.domain.business.operacao.service;

import org.springframework.stereotype.Service;

import br.com.bank.domain.business.operacao.model.Movimentacao;
import br.com.bank.domain.business.operacao.repository.MovimentacaoRepository;
import br.com.bank.domain.generic.service.CrudService;

@Service
public class MovimentacaoService extends CrudService<Movimentacao, Long, MovimentacaoRepository> {

   private static final long serialVersionUID = 1L;
   
}