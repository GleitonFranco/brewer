package com.algaworks.brewer.controller;

import javax.validation.Valid;

import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.EstiloFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.brewer.exception.CadastroEstiloException;
import com.algaworks.brewer.model.Estilo;
import com.algaworks.brewer.service.CadastroEstiloService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estilos")
public class EstilosController {
	@Autowired
	private CadastroEstiloService service;

	@Autowired
	private Estilos estilos;

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

	@GetMapping
    public ModelAndView pesquisar(EstiloFilter estiloFilter, @PageableDefault(size = 2) Pageable pageable) {
	    final ModelAndView mv = new ModelAndView("cerveja/PesquisaEstilos");
        Page<Estilo> pagina = this.estilos.filtrar(estiloFilter, pageable);
		mv.addObject("estilos", pagina);
        System.out.println("***** "+ pagina.getNumber() + " * " + pagina.getTotalPages() + " * " + pagina.getTotalElements() + " * " + pagina.getNumberOfElements());
	    return mv;
    }
}
