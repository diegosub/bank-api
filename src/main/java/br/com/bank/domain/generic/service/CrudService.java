package br.com.bank.domain.generic.service;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import br.com.bank.domain.exception.NegocioException;

/**
 * Classe extenda o CrudService deve ser annotada com @Transaction apontando
 * para o transctionManager que deseja usar na classe, caso não seja annotada
 * com @Transaction, os metodos não irão abrir transação a não ser que já
 * estejam incluidos em uma transação
 */
public abstract class CrudService<Entity, IdClass extends Serializable, Repository extends JpaRepository<Entity, IdClass>>
      extends GenericService<Entity, IdClass> {

   private static final long serialVersionUID = 1L;

   @Autowired
   protected Repository repository;

   @Transactional
   public Entity getById(IdClass id) {
      Entity retorno = repository.findById(id).orElseThrow(() -> new NegocioException("Entidade não encontrada."));
      return retorno;
   }

   @Transactional
   public Entity inserir(Entity entity) {
      validarInserir(entity);
      completarInserir(entity);
      repository.save(entity);
      return entity;
   }

   @Transactional
   public Entity alterar(Entity entity) {
      validarAlterar(entity);
      completarAlterar(entity);
      repository.save(entity);
      return entity;
   }

   public void completarInserir(Entity entity) {
   }

   public void completarAlterar(Entity entity) {
   }

   public void validarInserir(Entity entity) {
   }

   public void validarAlterar(Entity entity) {
   }
}