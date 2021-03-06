package com.bsimm.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bsimm.algafood.domain.exception.EntidadeEmUsoException;
import com.bsimm.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.bsimm.algafood.domain.model.Cidade;
import com.bsimm.algafood.domain.repository.CidadeRepository;
import com.bsimm.algafood.domain.service.CadastraCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadesController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastraCidadeService cadastraCidadeService;
	
	@GetMapping
	public List<Cidade> listar(){
		return this.cidadeRepository.listar();
	}
	
	@GetMapping("{cidadeId}")
	public ResponseEntity<Cidade> getById(@PathVariable Long cidadeId) {
		
		Cidade cidade = this.cidadeRepository.buscar(cidadeId);
		
		if (cidade == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cidade);
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Cidade cidade) {
		try {
			cidade = this.cadastraCidadeService.salvar(cidade);
			return ResponseEntity.ok(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PutMapping("/{cidadeId}")
	public ResponseEntity<?> atualizar(@RequestBody Cidade cidade, @PathVariable Long cidadeId){

		try {
			cidade = this.cadastraCidadeService.atualizar(cidade, cidadeId);
			return ResponseEntity.ok(cidade);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} 
	}
	
	@DeleteMapping("/{cidadeId}")
	public ResponseEntity<?> remover(@PathVariable Long cidadeId) {
		try {
			this.cadastraCidadeService.remover(cidadeId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	

}
