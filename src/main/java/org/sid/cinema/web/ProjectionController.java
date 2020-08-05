package org.sid.cinema.web;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.SeanceRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Place;
import org.sid.cinema.entities.Projection;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Ticket;
import org.sid.cinema.entities.Seance;
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
public class ProjectionController {
	
	@Autowired
	ProjectionRepository projectionRepository;
	@Autowired
	SalleRepository salleRepository;
	@Autowired
	FilmRepository filmRepository;
	@Autowired 
	SeanceRepository seanceRepository;
	@Autowired
	TicketRepository ticketRepository;
	@Autowired
	PlaceRepository placeRepository;
	
	@RequestMapping(value = "/saveProjection", method = RequestMethod.POST)
	public String save(Model model,  Projection projection) {
		model.addAttribute("projection", projection);
		projectionRepository.save(projection);
		int nbrPlace= projection.getSalle().getNombrePlace();
		for(int i = 1 ;i<=nbrPlace;i++) {
			Place place  = new Place(null,i,projection.getSalle(),null);
			placeRepository.save(place);
			Long j=Long.valueOf(i) ;
			ticketRepository.save(new Ticket(j,null,projection.getPrix(),null,false,place,projection));
		}
		return "projections/addProjection";
	}
	@RequestMapping(value = "/addProjection", method = RequestMethod.GET)
	public String formProjection(Model model) {
		model.addAttribute("projection", new Projection());
        
		List<Salle> listSalles = salleRepository.findAll();
		model.addAttribute("salles", listSalles);
		List<Film> listFilms = filmRepository.findAll();
		model.addAttribute("films", listFilms);
		List<Seance> listSeances = seanceRepository.findAll();
		model.addAttribute("seances", listSeances);

		return "projections/addProjection";
	}
	@GetMapping(path = "/allprojections")
	public String list(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "7") int size,
			@RequestParam(name = "motCle", defaultValue = "0.0") double motCle) {
		Page<Projection> pageProjections = projectionRepository.findBydoubleContains(motCle, PageRequest.of(page, size));
	    List<Projection> projections = projectionRepository.findAll();
		model.addAttribute("projections", pageProjections);
		model.addAttribute("pages", new int[pageProjections.getTotalPages()]);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		model.addAttribute("motCle", motCle);
		return "projections/projections";
	}

}
