package es.dsw.controllers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {	
	@MessageMapping("/join")
	@SendTo("/topic/player")
	public String jugadorSeUne(String data) {
		return data;
	}
	@MessageMapping("/exit")
	@SendTo("/topic/player")
	public String jugadorAbandona(String data) {
		return data;
	}
	@MessageMapping("/start")
	@SendTo("/topic/start")
	public String empezarPartida(String data) {
		return data;
	}	
	@MessageMapping("/results")
	@SendTo("/topic/results")
	public String mostrarResultados(String data) {
		return data;
	}
	@MessageMapping("/end")
	@SendTo("/topic/results")
	public String mostrarResultadoFinal(String data) {
		return data;
	}
}
