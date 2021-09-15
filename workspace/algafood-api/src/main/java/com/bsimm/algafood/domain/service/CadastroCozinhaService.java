package com.bsimm.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bsimm.algafood.domain.exception.EntidadeEmUsoException;
import com.bsimm.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.bsimm.algafood.domain.model.Cozinha;
import com.bsimm.algafood.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {
	
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	public CadastroCozinhaService(CozinhaRepository cozinhaRepository) {
		this.cozinhaRepository = cozinhaRepository;
	}

	public Cozinha salvar(Cozinha cozinha) {
		return this.cozinhaRepository.salvar(cozinha);
	}
	
	public void excluir(Long cozinhaId) {
		
		try {
			this.cozinhaRepository.remover(cozinhaId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Cozinha de código %d não existe.", cozinhaId));
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de código %d não pode ser removida, pois está em uso.", cozinhaId));
		}
	}
	

}
