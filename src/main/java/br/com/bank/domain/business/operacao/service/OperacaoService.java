package br.com.bank.domain.business.operacao.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.bank.domain.business.operacao.enums.TipoOperacaoEnum;
import br.com.bank.domain.business.operacao.model.Conta;
import br.com.bank.domain.business.operacao.model.Movimentacao;
import br.com.bank.domain.business.operacao.model.Operacao;
import br.com.bank.domain.business.operacao.repository.MovimentacaoRepository;
import br.com.bank.domain.business.operacao.repository.OperacaoRepository;
import br.com.bank.domain.exception.NegocioException;
import br.com.bank.domain.generic.service.CrudService;

@Service
public class OperacaoService extends CrudService<Operacao, Long, OperacaoRepository> {

   private static final long serialVersionUID = 1L;

   @Autowired
   private MovimentacaoRepository movimentacaoRepository;

   @Autowired
   private ContaService contaService;

   @Override
   @Transactional
   public Operacao inserir(Operacao model) {
      repository.save(model);

      if(model.getTipo().equals(TipoOperacaoEnum.DEPOSITO.getTipo())) {
         Movimentacao movimentacao = new Movimentacao();
         movimentacao.setOperacaoId(model.getId());
         movimentacao.setContaId(model.getContaId());
         movimentacao.setValor(model.getValor());
         movimentacaoRepository.save(movimentacao);

         Conta conta = contaService.obterPorId(model.getContaId());
         conta.setSaldo(conta.getSaldo().doubleValue() + model.getValor().doubleValue());
         if(conta.getSaldo().doubleValue() < 0) {
            throw new NegocioException("A conta não pode ficar negativa.");
         }
         contaService.alterar(conta);
      } else {
         if(model.getTipo().equals(TipoOperacaoEnum.TRANSFERENCIA.getTipo())) {
            Movimentacao movimentacaoSaida = new Movimentacao();
            movimentacaoSaida.setOperacaoId(model.getId());
            movimentacaoSaida.setContaId(model.getContaId());
            movimentacaoSaida.setValor(model.getValor() * -1);
            movimentacaoRepository.save(movimentacaoSaida);

            Conta contaSaida = contaService.obterPorId(model.getContaId());
            contaSaida.setSaldo(contaSaida.getSaldo().doubleValue() - model.getValor().doubleValue());
            if(contaSaida.getSaldo().doubleValue() < 0) {
               throw new NegocioException("A conta de origem da transferência não pode ficar negativa.");
            }
            contaService.alterar(contaSaida);

            Movimentacao movimentacaoEntrada = new Movimentacao();
            movimentacaoEntrada.setOperacaoId(model.getId());
            movimentacaoEntrada.setContaId(model.getContaDestId());
            movimentacaoEntrada.setValor(model.getValor());
            movimentacaoRepository.save(movimentacaoEntrada);

            Conta contaEntrada = contaService.obterPorId(model.getContaDestId());
            contaEntrada.setSaldo(contaEntrada.getSaldo().doubleValue() + model.getValor().doubleValue());
            if(contaSaida.getSaldo().doubleValue() < 0) {
               throw new NegocioException("A conta de origem da transferência não pode ficar negativa.");
            }
            contaService.alterar(contaEntrada);
         }
      }
      return model;
   }


}