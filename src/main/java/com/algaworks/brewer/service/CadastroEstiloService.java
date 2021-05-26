package com.algaworks.brewer.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.brewer.exception.CadastroEstiloException;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.repository.Estilos;

@Service
public class CadastroEstiloService {

	@Autowired
	private Estilos estilos;
	
	@Transactional
	public Estilo salvar(Estilo estilo) {
		System.out.println("Início service");
		final Optional<Estilo> estiloExistente = this.estilos.findByNomeIgnoreCase(estilo.getNome());
		if (estiloExistente.isPresent()) {
			System.out.println("Nome de estilo já existente!");
			throw new CadastroEstiloException("Nome de estilo já existente!");
		} else {
			System.out.println("Nome de estilo salvo!");
			return estilos.saveAndFlush(estilo);
		}
		
	}
}
