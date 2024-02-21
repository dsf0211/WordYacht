package es.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.dsw.models.Partida;
import es.dsw.models.Usuario;
import es.dsw.services.PartidaService;
import es.dsw.services.UsuarioService;

@RestController
public class GameController2 {
	@Autowired 
	private UsuarioService servicioUsuarios;
	@Autowired
	private PartidaService partidaService;
	
	@PostMapping(value = "/startGame")
	public void finalizarPartida(@RequestParam(name = "idPartida") int idPartida) {
		Partida partidaActual = partidaService.getGame(idPartida).get(); 
		partidaActual.setEstado("iniciada");
		partidaService.addGame(partidaActual);
	}
	
	@PostMapping(value="/exitGame")
	public void salirPartida(@RequestParam(name = "idPartida") int idPartida,
							 @RequestParam(name = "userID") int idUser,
							 @RequestParam(name = "finalizada") boolean finalizada,
							 @RequestParam(name = "puntosPartida") int puntosPartida) {
		Partida partidaActual = partidaService.getGame(idPartida).get();
		Usuario user = servicioUsuarios.getUser(idUser).get();
		if (finalizada) {
			//Se actualiza el estado de la partida a "finalizada"
			partidaActual.setEstado("finalizada");
			partidaService.addGame(partidaActual);
			//Se actualiza la puntuacion de la partida de cada jugador
			user.puntosPartida(partidaActual, puntosPartida);
			servicioUsuarios.addUser(user);
		} else {
			//Se borra el registro que relaciona el usuario con la partida
			user.salirDePartida(idPartida);
			servicioUsuarios.addUser(user);
		}
	}
}
