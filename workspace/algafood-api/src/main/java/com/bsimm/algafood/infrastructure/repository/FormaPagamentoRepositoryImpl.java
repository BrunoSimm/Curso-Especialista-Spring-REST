package com.bsimm.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.bsimm.algafood.domain.model.FormaPagamento;
import com.bsimm.algafood.domain.repository.FormaPagamentoRepository;

@Repository
public class FormaPagamentoRepositoryImpl implements FormaPagamentoRepository {

	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<FormaPagamento> listar() {
		return entityManager.createQuery("from FormaPagamento",FormaPagamento.class).getResultList();
	}

	@Override
	public FormaPagamento buscar(Long id) {
		return entityManager.find(FormaPagamento.class, id);
	}

	@Override
	@Transactional
	public FormaPagamento salvar(FormaPagamento formaPagamento) {
		return entityManager.merge(formaPagamento);
	}

	@Override
	@Transactional
	public void remover(FormaPagamento permissao) {
		permissao = this.buscar(permissao.getId());
		entityManager.remove(permissao);
	}

}
