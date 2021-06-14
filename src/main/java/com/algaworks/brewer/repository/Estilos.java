package com.algaworks.brewer.repository;

import java.util.Optional;

import com.algaworks.brewer.repository.helper.estilo.EstilosQueries;
import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.brewer.model.Estilo;

public interface Estilos extends JpaRepository<Estilo, Long>, EstilosQueries {
	
	public Optional<Estilo> findByNomeIgnoreCase(String nome);

}
