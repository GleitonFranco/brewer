package com.algaworks.brewer.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.exception.CadastroEstiloException;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.service.CadastroEstiloService;

@Controller
@RequestMapping("/estilos")
public class EstilosController {
	@Autowired
	private CadastroEstiloService service;

	@RequestMapping("/novo")
	public ModelAndView novo(Estilo estilo) {
		return new ModelAndView("estilo/CadastroEstilo");
	}
	
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Estilo estilo, BindingResult result, Model model, RedirectAttributes attributes) {
		System.out.println("Início cadastro");
		if (result.hasErrors()) {
			return this.novo(estilo);
		}
		System.out.println("Início salvar");
		try {			
			this.service.salvar(estilo);
			attributes.addFlashAttribute("mensagem", "Estilo cadastrado com sucesso: "+estilo.getNome());
		} catch (CadastroEstiloException e) {
			result.rejectValue("nome", e.getMessage(), e.getMessage());
			return this.novo(estilo);
		}
		return new ModelAndView("redirect:/estilos/novo");
	}
	
	@RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody ResponseEntity<?> cadastroRapido(@RequestBody @Valid Estilo estilo, BindingResult result) {
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body("Erro ao salvar estilo.");
		}
		Estilo estiloSalvo = null;
		System.out.println(estilo.getNome());
//		try {
			estiloSalvo = this.service.salvar(estilo);
//		} catch (CadastroEstiloException e) {			
//			return ResponseEntity.badRequest().body("Erro ao cadastrar estilo no banco de dados!");
//		}
		return ResponseEntity.ok(estiloSalvo);
	}

}
