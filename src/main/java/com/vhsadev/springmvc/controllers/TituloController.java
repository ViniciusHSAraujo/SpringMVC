package com.vhsadev.springmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.vhsadev.springmvc.models.Titulo;
import com.vhsadev.springmvc.repositories.TituloRepository;

@RequestMapping("/titulos")
@Controller
public class TituloController {
	
	@Autowired
	private TituloRepository tituloRepository;
	/**
	 * Renderiza a tela de cadastro de títulos.
	 */
	@RequestMapping("/novo")
	public String novo() {
		return "CadastroTitulo";
	}

	/**
	 * Recebe um título (via POST) e salva no banco de dados.
	 * @param titulo
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public String novo(Titulo titulo) {
		tituloRepository.save(titulo);
		return "CadastroTitulo";
	}
}
