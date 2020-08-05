package org.sid.cinema.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

	@RequestMapping(value = "/login")
	public String login() {
		return "user/login";
	}
	@RequestMapping(value = "/")
	public String redirect() {
		return "redirect:/index";
	}
	@RequestMapping(value = "/403")
	public String error() {
		return "403";
	}
	@RequestMapping(value = "/index")
	public String index() {
		return "index";
	}
}
