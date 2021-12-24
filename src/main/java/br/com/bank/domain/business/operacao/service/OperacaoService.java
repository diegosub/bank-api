package br.com.bank.domain.business.operacao.service;

import org.springframework.stereotype.Service;

import br.com.bank.domain.business.operacao.model.Operacao;
import br.com.bank.domain.business.operacao.repository.OperacaoRepository;
import br.com.bank.domain.generic.service.CrudService;

@Service
public class OperacaoService extends CrudService<Operacao, Long, OperacaoRepository> {

   private static final long serialVersionUID = 1L;
   
}