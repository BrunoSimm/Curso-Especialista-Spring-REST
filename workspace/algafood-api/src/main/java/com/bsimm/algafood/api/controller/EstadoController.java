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
import com.bsimm.algafood.domain.model.Estado;
import com.bsimm.algafood.domain.repository.EstadoRepository;
import com.bsimm.algafood.domain.service.CadastrarEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
	
	
	@Autowired
	private CadastrarEstadoService cadastrarEstadoService;

	@Autowired
	private EstadoRepository estadoRepository;
	
	@GetMapping
	public List<Estado> listar(){
		return this.estadoRepository.listar();
	}
	
	@GetMapping("/{estadoId}")
	public Estado getById(@PathVariable Long estadoId) {
		return this.estadoRepository.buscar(estadoId);
	}
	
	@PostMapping
	public ResponseEntity<Estado> adicionar(@RequestBody Estado estado) {
		estado = this.cadastrarEstadoService.salvar(estado);
		return ResponseEntity.ok(estado);
	}
	
	@PutMapping("/{estadoId}")
	public ResponseEntity<Estado> atualizar(@RequestBody Estado estado, @PathVariable Long estadoId){
		try {
			estado = this.cadastrarEstadoService.atualizar(estado, estadoId);
			return ResponseEntity.ok(estado);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/{estadoId}")
	public ResponseEntity<?> remover(@PathVariable Long estadoId) {
		try {
			this.cadastrarEstadoService.remover(estadoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
		}
	}
	
}
