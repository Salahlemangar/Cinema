package org.sid.cinema.web;

import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Place;
import org.sid.cinema.entities.Salle;
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
public class SalleController {
	
	@Autowired
	SalleRepository salleRepository;
	@Autowired
	CinemaRepository cinemaRepository;
	@Autowired 
	PlaceRepository placeRepository;

	@RequestMapping(value = "/saveSalle", method = RequestMethod.POST)
	public String save(Model model, @Valid Salle salle, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "salles/addSalle";
		salleRepository.save(salle);
		int nbrPlace = salle.getNombrePlace();
		for(int i =1; i<=nbrPlace;i++) {
			placeRepository.save(new Place(null,i,salle,null));
		}
		return "salles/addSalle";
	}

	@RequestMapping(value = "/addSalle", method = RequestMethod.GET)
	public String formSalle(Model model) {
		model.addAttribute("salle", new Salle());
		List<Cinema> list = cinemaRepository.findAll();
		model.addAttribute("cinemas", list);
		return "salles/addSalle";
	}
	@RequestMapping(value = "/deleteSalle",method = RequestMethod.GET)
	public String delete(@RequestParam(name = "salleId")Long id, String motCle, int page , int size) {
		salleRepository.deleteById(id);
		return "redirect:/allsalles?currentPage="+page+"&size="+size+"&motCle="+motCle;
		
	}
	@RequestMapping(value = "/editSalle", method=RequestMethod.GET)
	public String edit(Model model,Long id) {
		Salle s= salleRepository.findOne(id);
		model.addAttribute("salle", s);
		List<Cinema> list = cinemaRepository.findAll();
		model.addAttribute("cinemas", list);
		return "salles/editSalle";	
	}
	@GetMapping(path = "/allsalles")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "7") int size,
			@RequestParam(name = "motCle", defaultValue = "") String motCle) {
		Page<Salle> pageSalles = salleRepository.findByNameContains("%"+motCle+"%", PageRequest.of(page, size));
		model.addAttribute("salles", pageSalles.getContent());
		model.addAttribute("pages", new int[pageSalles.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("motCle", motCle);
		return "salles/salles";
	}
}
