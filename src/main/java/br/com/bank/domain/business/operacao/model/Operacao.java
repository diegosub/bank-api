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
@Table(name = "tb_operacao", schema = "operacao")
@SequenceGenerator(name = "sq_operacao", sequenceName = "operacao.sq_operacao", allocationSize = 1)
@Setter
@Getter
@Proxy(lazy = true)
public class Operacao
{
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_operacao")
   private Long id;

   @Column(name = "conta_id")
   private Long contaId;
   @Column(name = "conta_dest_id")
   private Long contaDestId;
   private String tipo;
   private String descricao;
   private Double valor;
   
   @CreationTimestamp
   @Column(name = "created_at")
   private Date createdAt;

}
