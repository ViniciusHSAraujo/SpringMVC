package com.vhsadev.springmvc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.vhsadev.springmvc.models.Titulo;
import com.vhsadev.springmvc.models.enums.StatusTitulo;
import com.vhsadev.springmvc.repositories.TituloRepository;

@RequestMapping("/titulos")
@Controller
public class TituloController {
	
	/**
	 * Injeção de dependência do repositório de títulos.
	 */
	@Autowired
	private TituloRepository tituloRepository;
	
	/**
	 * Renderiza a tela de pesquisa de títulos.
	 */
	@RequestMapping("")
	public ModelAndView pesquisa() {
		
		List<Titulo> titulos = tituloRepository.findAll();
		ModelAndView vm = new ModelAndView("PesquisaTitulos");
		vm.addObject("titulos", titulos);
		return vm;
	}
	
	/**
	 * Renderiza a tela de cadastro de títulos.
	 */
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView vm = new ModelAndView("CadastroTitulo");
		vm.addObject("statusTituloValues", StatusTitulo.values());
		return vm;
	}

	/**
	 * Recebe um título (via POST) e salva no banco de dados.
	 * @param titulo
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(Titulo titulo) {
		tituloRepository.save(titulo);
		
		ModelAndView vm = new ModelAndView("CadastroTitulo");
		vm.addObject("MSG_Sucesso", "Título salvo com sucesso!");
		return vm;
	}
}
