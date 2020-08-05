package org.sid.cinema.web;


import java.util.List;

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
public class CinemaController {
	@Autowired
	CinemaRepository cinemaRepository;
	@Autowired
	VilleRepository villeRepository;
	
	@RequestMapping(value = "/saveCinema", method = RequestMethod.POST)
	public String save(Model model, @Valid Cinema cinema, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "cinemas/addCinema";
		cinemaRepository.save(cinema);

		return "cinemas/addCinema";

	}

	@RequestMapping(value = "/addCinema", method = RequestMethod.GET)
	public String formCinema(Model model) {
		model.addAttribute("cinema", new Cinema());

		List<Ville> list = villeRepository.findAll();
		model.addAttribute("villes", list);

		return "cinemas/addCinema";
	}
	@RequestMapping(value = "/deleteCinema",method = RequestMethod.GET)
	public String delete(@RequestParam(name = "cinemaId")Long id, String motCle, int page , int size) {
		cinemaRepository.deleteById(id);
		return "redirect:/allcinemas?currentPage="+page+"&size="+size+"&motCle="+motCle;
		
	}
	@RequestMapping(value = "/editCinema", method=RequestMethod.GET)
	public String edit(Model model,Long id) {
		Cinema c= cinemaRepository.findOne(id);
		model.addAttribute("cinema", c);
		List<Ville> list = villeRepository.findAll();
		model.addAttribute("villes", list);
		return "cinemas/editCinema";
		
		
	}

	@GetMapping(path = "/allcinemas")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "7") int size,
			@RequestParam(name = "motCle", defaultValue = "") String motCle) {
		Page<Cinema> pageCinemas = cinemaRepository.findByNameContains("%"+motCle+"%", PageRequest.of(page, size));
		model.addAttribute("cinemas", pageCinemas.getContent());
		model.addAttribute("pages", new int[pageCinemas.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("motCle", motCle);
		return "cinemas/cinemas";
	}
	
  
	

}
