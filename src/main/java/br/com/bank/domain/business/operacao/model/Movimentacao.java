package br.com.bank.domain.business.operacao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Proxy;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_movimentacao", schema = "operacao")
@SequenceGenerator(name = "sq_movimentacao", sequenceName = "operacao.sq_movimentacao", allocationSize = 1)
@Setter
@Getter
@Proxy(lazy = true)
public class Movimentacao
{
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_movimentacao")
   private Long id;
   
   @Column(name = "conta_id")
   private Long contaId;
   
   @Column(name = "operacao_id")
   private Long operacaoId;
   
   private Double valor;
   
   @CreationTimestamp
   @Column(name = "created_at")
   private Date createdAt;
   
}
