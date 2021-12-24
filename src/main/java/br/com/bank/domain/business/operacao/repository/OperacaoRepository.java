package br.com.bank.domain.business.operacao.repository;

import org.springframework.context.annotation.Lazy;

import br.com.bank.domain.business.operacao.model.Operacao;
import br.com.bank.domain.generic.repository.GenericRepository;

@Lazy(true)
public interface OperacaoRepository extends GenericRepository<Operacao, Long> {}