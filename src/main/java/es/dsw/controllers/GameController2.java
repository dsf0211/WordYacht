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
	
	//Empezar la partida 
	@PostMapping(value = "/startGame")
	public void finalizarPartida(@RequestParam(name = "idPartida") int idPartida) {
		Partida partidaActual = partidaService.getGame(idPartida).get(); 
		//Se actualiza el estado de la partida a "iniciada"
		partidaActual.setEstado("iniciada");
		partidaService.addGame(partidaActual);
	}
	
	//Perder la puntuacion de la partida
	@PostMapping(value="/deleteGame")
	public void salirPartida(@RequestParam(name = "idPartida") int idPartida,
							 @RequestParam(name = "userID") int idUser,
							 @RequestParam(name = "puntosPartida") int puntosPartida) {
		Usuario user = servicioUsuarios.getUser(idUser).get();
		//Se borra el registro que relaciona el usuario con la partida
		user.salirDePartida(idPartida);
		servicioUsuarios.addUser(user);
	}
	//Guardar la puntuacion de la partida
	@PostMapping(value="/saveGame")
	public void guardarPartida(@RequestParam(name = "idPartida") int idPartida,
			 				   @RequestParam(name = "userID") int idUser,
			 				   @RequestParam(name = "puntosPartida") int puntosPartida) {
		Partida partidaActual = partidaService.getGame(idPartida).get();
		Usuario user = servicioUsuarios.getUser(idUser).get();
		//Se actualiza el estado de la partida a "finalizada"
		partidaActual.setEstado("finalizada");
		partidaService.addGame(partidaActual);
		//Se actualiza la puntuacion de la partida de cada jugador
		user.puntosPartida(partidaActual, puntosPartida);
		servicioUsuarios.addUser(user);
	}
}
