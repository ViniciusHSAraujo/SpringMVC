package com.vhsadev.springmvc.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		vm.addObject("titulo", new Titulo());
		return vm;
	}

	/**
	 * Recebe um título (via POST) e salva no banco de dados.
	 * 
	 * @param titulo
	 */
	@RequestMapping(value = "/novo", method = RequestMethod.POST)
	public ModelAndView novo(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {

		ModelAndView vm = new ModelAndView("CadastroTitulo");
		vm.addObject("statusTituloValues", StatusTitulo.values());

		if (errors.hasErrors()) {
			vm.addObject("MSG_Erro", errors.getAllErrors());
			return vm;
		}

		tituloRepository.save(titulo);
		vm = new ModelAndView("redirect:/titulos/");
		attributes.addFlashAttribute("MSG_Sucesso", "Título salvo com sucesso!");
		return vm;
	}

	/**
	 * Renderiza a tela de EDIÇÃO de títulos.
	 */
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable long codigo) {

		Titulo titulo = tituloRepository.getOne(codigo);

		ModelAndView vm = new ModelAndView("CadastroTitulo");
		vm.addObject("statusTituloValues", StatusTitulo.values());
		vm.addObject("titulo", titulo);
		return vm;
	}

	/**
	 * Recebe um título (via POST) e edita no banco de dados.
	 * 
	 * @param titulo
	 */
	@RequestMapping(value = "{codigo}", method = RequestMethod.POST)
	public ModelAndView edicao(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {

		ModelAndView vm = new ModelAndView("CadastroTitulo");
		vm.addObject("statusTituloValues", StatusTitulo.values());

		if (errors.hasErrors()) {
			vm.addObject("MSG_Erro", errors.getAllErrors());
			return vm;
		}

		tituloRepository.save(titulo);
		vm = new ModelAndView("redirect:/titulos/");
		attributes.addFlashAttribute("MSG_Sucesso", "Título editado com sucesso!");
		return vm;
	}

	/**
	 * Recebe um Id (via POST) e exclui no banco de dados.
	 * 
	 * @param titulo
	 */
	@RequestMapping(value = "/excluir/{codigo}", method = RequestMethod.POST)
	public ModelAndView exclusao(@PathVariable long codigo, RedirectAttributes attributes) {

		ModelAndView vm = new ModelAndView("redirect:/titulos/");

		try {
			tituloRepository.deleteById(codigo);
			attributes.addFlashAttribute("MSG_Sucesso", "Título excluído com sucesso!");
		} catch (Exception e) {
			attributes.addFlashAttribute("MSG_Erro", "Impossível excluir esse título!");
		}

		return vm;

	}

	/**
	 * Recebe um Id (via POST) e exclui no banco de dados.
	 * 
	 * @param titulo
	 */
	@RequestMapping(value = "/receber/{codigo}", method = RequestMethod.POST)
	public ModelAndView recebimento(@PathVariable long codigo, RedirectAttributes attributes) {

		ModelAndView vm = new ModelAndView("redirect:/titulos/");

		Optional<Titulo> tituloBusca = tituloRepository.findById(codigo);
		if (!tituloBusca.isPresent()) {
			attributes.addFlashAttribute("MSG_Erro", "Título não encontrado!");
			return vm;
		}

		Titulo titulo = tituloBusca.get();
		if (titulo.isPendente()) {
			titulo.setStatus(StatusTitulo.RECEBIDO);
			tituloRepository.save(titulo);
			
			attributes.addFlashAttribute("MSG_Sucesso", "Título marcado como recebido com sucesso!");
		} else {
			attributes.addFlashAttribute("MSG_Erro", "Esse título já havia sido recebido anteriormente!");
		}

		return vm;

	}
}
