package es.dsw.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
	
	@MessageMapping("/join")
	@SendTo("/topic/player")
	public String jugadorUnido(String data) {
		return data;
	}
}