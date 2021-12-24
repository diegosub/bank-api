package br.com.bank.domain.business.operacao.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.bank.domain.business.seguranca.model.Usuario;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_conta", schema = "operacao")
@SequenceGenerator(name = "sq_conta", sequenceName = "operacao.sq_conta", allocationSize = 1)
@Setter
@Getter
@Proxy(lazy = true)
public class Conta
{
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_conta")
   private Long id;
   
   @Column(name = "usuario_id")
   private Long usuarioId;
   
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name = "usuario_id", insertable = false, updatable = false)   
   private Usuario usuario;
   
   private Double saldo;
   
   @CreationTimestamp
   @Column(name = "created_at")
   private Date createdAt;
   
   @UpdateTimestamp
   @Column(name = "updated_at")
   private Date updatedAt;
}
