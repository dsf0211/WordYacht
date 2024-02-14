package es.dsw.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import es.dsw.models.Partida;
import es.dsw.models.Usuario;
import es.dsw.services.PartidaService;
import es.dsw.services.UsuarioService;

@Controller
public class GameController {
	@Autowired
	private PartidaService partidaService;
	@Autowired
	private UsuarioService servicioUsuarios;
	private Partida partida;

	// Asociar la partida con el usuario
	public void asociarUsuarioPartida(int idUsuario, Partida partida) {
		Usuario usuario = servicioUsuarios.getUser(idUsuario).orElse(null);
		usuario.setPartidaActual(partida, 0);
		servicioUsuarios.addUser(usuario);
	}

	// Obtener todos los jugadores asociados a la partida
	public ArrayList<Usuario> jugadoresPartida(int codigo) {
		ArrayList<Usuario> jugadores = new ArrayList<Usuario>();
		for (Usuario jugador : servicioUsuarios.getAll()) {
			for (Entry<Partida, Integer> entry : jugador.getPuntosPorPartida().entrySet()) {
				Partida objPartida = entry.getKey();
				if (objPartida.getCodPrivada() == codigo) {
					jugadores.add(jugador);
				}
			}
		}
		return jugadores;
	}

	//Crear partida privada
	@GetMapping(value = "/createPrivate")
	public String CrearpartidaPrivada(@RequestParam(name = "idUser") int idUsuario) {
		partida = new Partida();
		// Almacenar códigos existentes de partidas no finalizadas
		Set<Integer> codigos = new HashSet<>();
		for (Partida e : partidaService.getAll()) {
			if (!e.getEstado().equals("finalizada")) {
				codigos.add(partida.getCodPrivada());
			}
		}
		// Generar codigo y asignarlo a la partida
		int codigo = new Random().nextInt(1000, 9999);
		while (codigos.contains(codigo)) {
			codigo = new Random().nextInt(1000, 9999);
		}
		partida.setCodPrivada(codigo);
		// Insertar la partida en la base de datos
		partidaService.addGame(partida);
		return "redirect:/joinPrivate?code="+codigo+"&idUser="+idUsuario;
	}

	//Unirse a partida privada
	@GetMapping(value = "/joinPrivate")
	public String partidaPrivada(@RequestParam(name = "code") int codigo,
								 @RequestParam(name = "idUser") int idUsuario,
								 Model model) {
		// Buscar partida privada según el código
		for (Partida e : partidaService.getAll()) {
			if (e.getEstado().equals("creada") && e.getCodPrivada() == codigo) {
				partida = e;			
				break;
			}
		}
		asociarUsuarioPartida(idUsuario, partida);
		model.addAttribute("jugadores", jugadoresPartida(codigo));
		return "juego";
	}

	//Unirse a partida pública
	@GetMapping(value = "/joinPublic")
	public String partidaPublica(@RequestParam(name = "idUser") int idUsuario,
			Model model) {
		partida = new Partida();
		boolean existe = false;
		// Buscar partida privada según el código
		for (Partida e : partidaService.getAll()) {
			if (e.getEstado().equals("creada") && e.getCodPrivada() == 0) {
				partida = e;
				existe = true;
				break;
			}
		}
		if (!existe) {
			partidaService.addGame(partida);
		}
		asociarUsuarioPartida(idUsuario, partida);
		model.addAttribute("jugadores", jugadoresPartida(0));
		return "juego";
	}
}
