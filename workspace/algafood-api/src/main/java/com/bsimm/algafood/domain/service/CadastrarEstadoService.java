package com.bsimm.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bsimm.algafood.domain.exception.EntidadeEmUsoException;
import com.bsimm.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.bsimm.algafood.domain.model.Estado;
import com.bsimm.algafood.domain.repository.EstadoRepository;

@Service
public class CadastrarEstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public Estado salvar(Estado estado) {
		return this.estadoRepository.salvar(estado);
	}
	
	public Estado atualizar(Estado estado, Long estadoId) {
			Estado estadoAtual = this.estadoRepository.buscar(estadoId);
			
			if (estadoAtual == null) {
				throw new EntidadeNaoEncontradaException(String.format("Estado com o código %d não existe", estado.getId()));
			}
			
			estado.setId(estadoId);
			BeanUtils.copyProperties(estado, estadoAtual,"id");
			
			return this.estadoRepository.salvar(estadoAtual);
	}
	
	public void remover(Long estadoId) {
		try {
			this.estadoRepository.remover(estadoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe um estado com o código %d", estadoId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removido, pois está em uso.", estadoId));
		}
		
	}
	

}
