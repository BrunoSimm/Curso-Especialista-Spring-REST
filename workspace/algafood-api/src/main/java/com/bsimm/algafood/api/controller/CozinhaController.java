package com.bsimm.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bsimm.algafood.domain.exception.EntidadeEmUsoException;
import com.bsimm.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.bsimm.algafood.domain.model.Cozinha;
import com.bsimm.algafood.domain.repository.CozinhaRepository;
import com.bsimm.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public List<Cozinha> listar(){
		return this.cozinhaRepository.listar(); 
	}
	
	@GetMapping("/{cozinhaId}")
	@ResponseStatus(code = HttpStatus.OK)
	public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
		Cozinha cozinha = this.cozinhaRepository.buscar(id);
		
		if(cozinha == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		return ResponseEntity.ok(cozinha);
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> adicionar(@RequestBody Cozinha cozinha) {
		cozinha = this.cadastroCozinha.salvar(cozinha);
		return ResponseEntity.status(HttpStatus.CREATED).body(cozinha);
	}
	
	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha){
		
		Cozinha cozinhaAtual = this.cozinhaRepository.buscar(cozinhaId);
		
		if (cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual,"id"); //Copia (set) os elementos para o objeto destino. "id" ser?? ignorado.
			this.cadastroCozinha.salvar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> delete(@PathVariable Long cozinhaId) {
		try {
			this.cadastroCozinha.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
				
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
			
		} catch(EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
}
