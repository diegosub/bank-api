package br.com.bank.domain.business.operacao.repository;

import org.springframework.context.annotation.Lazy;

import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.generic.repository.GenericRepository;

@Lazy(true)
public interface ContaRepository extends GenericRepository<Conta, Long> {}