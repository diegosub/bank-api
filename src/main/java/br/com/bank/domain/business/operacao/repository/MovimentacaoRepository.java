package br.com.bank.domain.business.operacao.repository;

import org.springframework.context.annotation.Lazy;

import br.com.bank.domain.business.operacao.model.Movimentacao;
import br.com.bank.domain.generic.repository.GenericRepository;

@Lazy(true)
public interface MovimentacaoRepository extends GenericRepository<Movimentacao, Long> {}