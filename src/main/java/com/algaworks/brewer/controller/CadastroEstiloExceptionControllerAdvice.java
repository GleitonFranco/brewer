package com.algaworks.brewer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.algaworks.brewer.exception.CadastroEstiloException;

@ControllerAdvice
public class CadastroEstiloExceptionControllerAdvice {

	@ExceptionHandler(CadastroEstiloException.class)
	public ResponseEntity<String> handleCadastroEstiloException(CadastroEstiloException e) {
		return ResponseEntity.badRequest().body("Erro ao cadastrar estilo no banco de dados!");
	}
}
