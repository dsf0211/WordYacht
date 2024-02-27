package es.dsw.controllers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {	
	//Un jugador se une a una partida
	@MessageMapping("/join")
	@SendTo("/topic/player")
	public String jugadorSeUne(String data) {
		return data;
	}
	//Un jugador sale de la partida
	@MessageMapping("/exit")
	@SendTo("/topic/player")
	public String jugadorAbandona(String data) {
		return data;
	}
	//Comienza la partida
	@MessageMapping("/start")
	@SendTo("/topic/start")
	public String empezarPartida(String data) {
		return data;
	}	
	//Se envían los resultados del jugador en la ronda
	@MessageMapping("/results")
	@SendTo("/topic/results")
	public String mostrarResultados(String data) {
		return data;
	}
	//Se envían los resultados del jugador en la partida
	@MessageMapping("/end")
	@SendTo("/topic/results")
	public String mostrarResultadoFinal(String data) {
		return data;
	}
}
