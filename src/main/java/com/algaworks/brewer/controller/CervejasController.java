package com.algaworks.brewer.controller;

import com.algaworks.brewer.controller.page.PageWrapper;
import com.algaworks.brewer.model.Cerveja;
import com.algaworks.brewer.model.Origem;
import com.algaworks.brewer.model.Sabor;
import com.algaworks.brewer.repository.Cervejas;
import com.algaworks.brewer.repository.Estilos;
import com.algaworks.brewer.repository.filter.CervejaFilter;
import com.algaworks.brewer.service.CadastroCervejaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/cervejas")
public class CervejasController {

	private static final Logger logger = LoggerFactory.getLogger(CervejasController.class);
	
	@Autowired
	private Cervejas cervejas;
	@Autowired
	private Estilos estilos;
	@Autowired
	private CadastroCervejaService cadastroCervejaService;
	
	@RequestMapping("/novo")
	public ModelAndView novo(Cerveja cerveja) {
		ModelAndView mv = new ModelAndView("cerveja/CadastroCerveja");
		mv.addObject("sabores", Sabor.values());
		mv.addObject("estilos", this.estilos.findAll());
		mv.addObject("origens", Origem.values());
//		logger.error("Aqui é um log de erro!!!!");
//		logger.info("Aqui é um log nível info.");
		logger.info("Cervejas: "+this.estilos.findAll().size());
		
		return mv;
	}

	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView cadastrar(@Valid Cerveja cerveja, BindingResult result, Model model, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
//			String s = result.getFieldError().getObjectName();
//			String s2 = result.getFieldError().getField();
//			String s3 = result.getFieldError().getDefaultMessage();
//			model.addAttribute("mensagem", s + "." + s2 + " : " + s3);
//			System.out.println(s + "." + s2 + " : " + s3);
			return this.novo(cerveja);
		}
		cerveja.setEstilo(this.estilos.findOne(cerveja.getEstilo().getCodigo()));
		cadastroCervejaService.salvar(cerveja);
		String okMsg = "OK: Cerveja salva com sucesso:";
		printMsg(okMsg);
		attributes.addFlashAttribute("mensagem", okMsg +cerveja.getSku());
		return new ModelAndView("redirect:/cervejas/novo");
	}

	private void printMsg(String okMsg) {
		System.out.println(okMsg);
		System.out.println(okMsg);
	}

	@GetMapping
    public ModelAndView pesquisar(CervejaFilter cervejaFilter, BindingResult result, @PageableDefault(size = 2) Pageable pageable, HttpServletRequest request) {
	    final ModelAndView mv = new ModelAndView("cerveja/PesquisaCervejas");
	    mv.addObject("estilos", this.estilos.findAll());
	    mv.addObject("origens", Origem.values());
        mv.addObject("sabores", Sabor.values());
        mv.addObject("cervejas", this.cervejas.filtrar(cervejaFilter, pageable));
//		Page<Cerveja> pagina = this.cervejas.filtrar(cervejaFilter, pageable);
		PageWrapper<Cerveja> paginaWrapper = new PageWrapper<Cerveja>(this.cervejas.filtrar(cervejaFilter, pageable), request);
		mv.addObject("pagina", paginaWrapper);
//        mv.addObject("cervejaFilter", new CervejaFilter());
	    return mv;
    }
	
	@RequestMapping(value = "cadastro-produto", method = RequestMethod.GET)
	public String cadastroProduto() {
		return "cerveja/cadastro-produto";
	}
	
}
