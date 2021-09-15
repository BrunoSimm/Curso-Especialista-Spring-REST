package com.bsimm.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.bsimm.algafood.domain.model.Restaurante;
import com.bsimm.algafood.domain.repository.RestauranteRepository;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepository {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Restaurante> listar(){
		TypedQuery<Restaurante> query = this.entityManager.createQuery("from Restaurante", Restaurante.class); //Cria a Query
		return query.getResultList();
	}
	
	@Override
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		return entityManager.merge(restaurante);
	}
	
	@Override
	public Restaurante buscar(Long id) {
		return entityManager.find(Restaurante.class, id);
	}
	
	@Override
	@Transactional
	public void remover(Restaurante restaurante) {
		restaurante = this.buscar(restaurante.getId());
		entityManager.remove(restaurante);
	}
	
}
