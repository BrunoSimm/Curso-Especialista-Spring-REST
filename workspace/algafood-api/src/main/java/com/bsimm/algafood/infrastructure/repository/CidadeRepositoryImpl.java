package com.bsimm.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;

import com.bsimm.algafood.domain.model.Cidade;
import com.bsimm.algafood.domain.repository.CidadeRepository;

@Repository
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<Cidade> listar() {
		return entityManager.createQuery("from Cidade",Cidade.class).getResultList();
	}

	@Override
	public Cidade buscar(Long id) {
		return entityManager.find(Cidade.class, id);
	}

	@Override
	@Transactional
	public Cidade salvar(Cidade cidade) {
		return entityManager.merge(cidade);
	}

	@Override
	@Transactional
	public void remover(Long id) {
		Cidade cidade = this.buscar(id);
		
		if (cidade == null) {
			throw new EmptyResultDataAccessException(1);
		}
		
		entityManager.remove(cidade);
	}

}
