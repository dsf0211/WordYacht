package es.dsw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
	
	@GetMapping(value = "/private")
	public String partidaPrivada() {
		return "juego";
	}
	
	@GetMapping(value = "/public")
	public String partidaPublica() {
		return "juego";
	}
}
