package br.com.bank.domain.business.seguranca.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario", schema = "seguranca")
@SequenceGenerator(name = "sq_usuario", sequenceName = "sq_usuario", allocationSize = 1)
@Setter
@Getter
@Proxy(lazy = true)
public class Usuario {

   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_usuario")
   private Long id;
   private Long cpf;
   private String nome;
   @JsonIgnore
   private String senha;

}