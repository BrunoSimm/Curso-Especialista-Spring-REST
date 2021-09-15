package com.bsimm.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.bsimm.algafood.domain.model.Cozinha;
import com.bsimm.algafood.domain.repository.CozinhaRepository;

@Repository
public class CozinhaRepositoryImpl implements CozinhaRepository {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Cozinha> listar(){
		TypedQuery<Cozinha> query = this.entityManager.createQuery("from Cozinha", Cozinha.class); //Cria a Query
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return entityManager.merge(cozinha);
	}
	
	@Override
	public Cozinha buscar(Long id) {
		return entityManager.find(Cozinha.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Long id) {
		Cozinha cozinha = this.buscar(id);
				
		if (cozinha == null) {
			throw new EmptyResultDataAccessException(1);
		}
				
		entityManager.remove(cozinha);
	}

	@Override
	public List<Cozinha> consultarPorNome(String nome) {
		return entityManager.createQuery("from Cozinha where nome = :nome",Cozinha.class)
				.setParameter("nome", nome)
				.getResultList();
	}

}
