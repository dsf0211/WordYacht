package es.dsw.controllers;

import java.util.ArrayList;
import java.util.List;

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
	@PostMapping(value = "/results")
	public List<Usuario> resultadosTurno(@RequestParam(name="userID") int idUser,
										@RequestParam(name="puntosTurno") int puntos,
										@RequestParam(name="categoria") String categoria,
										@RequestParam(name="palabras") String palabras
										) {
		ArrayList<Usuario> jugadores = new ArrayList<Usuario>();
		Usuario user = servicioUsuarios.getUser(idUser).get();
		user.setPuntos(puntos);
		user.setCategoria(categoria);
		user.setPalabras(palabras.split(","));
		jugadores.add(user);
		return jugadores;
	}
	
	@PostMapping(value = "/endGame")
	public List<Usuario> finalizarPartida(@RequestParam(name = "puntosPartida") int puntosPartida,
								 @RequestParam(name = "idPartida") int idPartida,
								 @RequestParam(name="userID") int idUser) {
		ArrayList<Usuario> jugadores = new ArrayList<Usuario>();
		Usuario user = servicioUsuarios.getUser(idUser).get();
		Partida partidaActual = partidaService.getGame(idPartida).get(); 
		user.setPuntos(puntosPartida);
		user.actualizarPuntos(partidaActual, puntosPartida);
		partidaActual.setEstado("finalizada");
		jugadores.add(user);
		servicioUsuarios.addUser(user);
		partidaService.addGame(partidaActual);
		return jugadores;
	}
}
