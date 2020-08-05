package org.sid.cinema.web;


import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.sid.cinema.dao.CategorieRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.entities.Categorie;
import org.sid.cinema.entities.Cinema;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Salle;
import org.sid.cinema.entities.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class FilmController {
	
	

	@Autowired
	FilmRepository filmRepository;
	@Autowired
	CategorieRepository categorieRepository;
	
	
	@RequestMapping(value = "/saveFilm", method = RequestMethod.POST)
	public String saveFilm(Film film, @RequestParam(name = "picture") MultipartFile file) throws Exception {
		
		
		
		if (!(file.isEmpty())) {
			film.setPhoto(file.getOriginalFilename());
			file.transferTo(new File(System.getProperty("user.home")+"/CinemaPictures/"+file.getOriginalFilename()));
			
		}
		filmRepository.save(film);
	
		return "redirect:/allfilms";
	}
	@RequestMapping(value="/getPhoto", produces = MediaType.IMAGE_JPEG_VALUE)
	@ResponseBody
	public byte[] getPhoto(String name) throws Exception {
		File file = new File(System.getProperty("user.home")+"/CinemaPictures/"+name);
		return org.apache.commons.io.IOUtils.toByteArray(new FileInputStream(file));
		
	}

	@RequestMapping(value = "/addFilm", method = RequestMethod.GET)
	public String formFilm(Model model) {
		model.addAttribute("film", new Film());
        
		List<Categorie> list = categorieRepository.findAll();
		model.addAttribute("categories", list);

		return "films/addFilm";
	}
	@GetMapping(value = "/allfilms" )
	public String list(Model model) {
		List<Film> list = filmRepository.findAll();
		model.addAttribute("films", list);
		return "films/films";
		
	}
	@RequestMapping(value = "/deleteFilm",method = RequestMethod.GET)
	public String delete(@RequestParam(name = "filmId")Long id) {
		filmRepository.deleteById(id);
		return "redirect:/allfilms";
		
	}
	@RequestMapping(value = "/editFilm", method=RequestMethod.GET)
	public String edit(Model model,Long id) {
		Film f= filmRepository.findOne(id);
		model.addAttribute("film", f);
		List<Categorie> list = categorieRepository.findAll();
		model.addAttribute("categories", list);
		return "films/editFilm";	
	}
}
