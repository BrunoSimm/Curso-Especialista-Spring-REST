package com.bsimm.algafood.domain.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bsimm.algafood.domain.exception.EntidadeEmUsoException;
import com.bsimm.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.bsimm.algafood.domain.model.Cidade;
import com.bsimm.algafood.domain.model.Estado;
import com.bsimm.algafood.domain.repository.CidadeRepository;
import com.bsimm.algafood.domain.repository.EstadoRepository;

@Service
public class CadastraCidadeService {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);
        
        if (estado == null) {
            throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de estado com código %d", estadoId));
        }
        
        cidade.setEstado(estado);
		return this.cidadeRepository.salvar(cidade);
	}

	public Cidade atualizar(Cidade cidade, Long cidadeId) {
		
		Cidade cidadeAtual = this.cidadeRepository.buscar(cidadeId);
		
		if (cidadeAtual == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com o código %d.", cidadeId));
		}
		
		Estado estado = this.estadoRepository.buscar(cidade.getEstado().getId());
		
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de estado com código %d.", cidade.getEstado().getId()));
		}
		
		cidade.setEstado(estado);
		BeanUtils.copyProperties(cidade, cidadeAtual,"id");
		
		return this.cidadeRepository.salvar(cidadeAtual);
	}

	public void remover(Long cidadeId) {
		try {
			this.cidadeRepository.remover(cidadeId);
			
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está em uso.", cidadeId));
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe uma cidade com o código %d", cidadeId));
		} 
		
	}

}
