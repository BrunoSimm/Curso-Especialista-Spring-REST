package com.bsimm.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.bsimm.algafood.domain.model.Permissao;
import com.bsimm.algafood.domain.repository.PermissaoRepository;

@Repository
public class PermissaoRepositoryImpl implements PermissaoRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<Permissao> listar() {
		return entityManager.createQuery("from Permissao",Permissao.class).getResultList();
	}

	@Override
	public Permissao buscar(Long id) {
		return entityManager.find(Permissao.class, id);
	}

	@Override
	@Transactional
	public Permissao salvar(Permissao permissao) {
		return entityManager.merge(permissao);
	}

	@Override
	@Transactional
	public void remover(Permissao permissao) {
		permissao = this.buscar(permissao.getId());
		entityManager.remove(permissao);
	}

}
