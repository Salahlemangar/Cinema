package org.sid.cinema.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VilleController {
	@Autowired
	VilleRepository villeRepository;
	@Autowired
	CinemaRepository cinemaRepository;

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(Model model, @Valid Ville ville, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "villes/addVille";
		villeRepository.save(ville);

		return "villes/addVille";

	}

	@RequestMapping(value = "/addVille", method = RequestMethod.GET)
	public String formVille(Model model) {
		model.addAttribute("ville", new Ville());
		return "villes/addVille";
	}
	@RequestMapping(value = "/delete",method = RequestMethod.GET)
	public String delete(@RequestParam(name = "villeId")Long id, String motCle, int page , int size) {
		villeRepository.deleteById(id);
		return "redirect:/allvilles?currentPage="+page+"&size="+size+"&motCle="+motCle;
		
	}
	@RequestMapping(value = "/edit", method=RequestMethod.GET)
	public String edit(Model model,Long id) {
		Ville v = villeRepository.findOne(id);
		model.addAttribute("ville", v);
		return "villes/editVille";
		
		
	}

	@GetMapping(path = "/allvilles")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "7") int size,
			@RequestParam(name = "motCle", defaultValue = "") String motCle) {
		Page<Ville> pageVilles = villeRepository.findByNameContains("%"+motCle+"%", PageRequest.of(page, size));
		model.addAttribute("villes", pageVilles.getContent());
		model.addAttribute("pages", new int[pageVilles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("motCle", motCle);
		return "villes/villes";
	}

}
