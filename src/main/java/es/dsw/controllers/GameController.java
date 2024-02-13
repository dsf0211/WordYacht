package es.dsw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
	
	@GetMapping(value = "/results")
	public String resultadosTurno() {
		return "resultados";
	}
}
