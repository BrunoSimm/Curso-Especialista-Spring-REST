package com.bsimm.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bsimm.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.bsimm.algafood.domain.model.Cozinha;
import com.bsimm.algafood.domain.model.Restaurante;
import com.bsimm.algafood.domain.repository.CozinhaRepository;
import com.bsimm.algafood.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();
		
		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId); 
		
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d", cozinhaId));
		}
		
		restaurante.setCozinha(cozinha);
		
		return this.restauranteRepository.salvar(restaurante);
	}

	public Restaurante atualizar(Long restauranteId, Restaurante restaurante) {
		
		Cozinha cozinha = cozinhaRepository.buscar(restaurante.getCozinha().getId()); 
		
		if (cozinha == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d", restaurante.getCozinha().getId()));
		}
		
		Restaurante restauranteOriginal = this.restauranteRepository.buscar(restauranteId);
		
		if (restauranteOriginal == null) {
			return null;
		}
		
		BeanUtils.copyProperties(restaurante, restauranteOriginal,"id","cozinha"); //ignore propertys id and cozinha
		
		return this.restauranteRepository.salvar(restauranteOriginal);
	}

	
	
	

}
