package com.bsimm.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.bsimm.algafood.domain.model.Estado;
import com.bsimm.algafood.domain.repository.EstadoRepository;

@Repository
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<Estado> listar() {
		return entityManager.createQuery("from Estado",Estado.class).getResultList();
	}

	@Override
	public Estado buscar(Long id) {
		return entityManager.find(Estado.class, id);
	}

	@Override
	@Transactional
	public Estado salvar(Estado estado) {
		return entityManager.merge(estado);
	}

	@Override
	@Transactional
	public void remover(Long estadoId) {
		Estado estado = this.buscar(estadoId);
		
		if (estado == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		entityManager.remove(estado);
	}

}
