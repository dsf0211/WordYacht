package es.dsw.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.dsw.SecurityConfiguration;
import es.dsw.models.Palabra;
import es.dsw.models.Partida;
import es.dsw.models.Usuario;
import es.dsw.services.PalabraService;
import es.dsw.services.UsuarioService;

//Controladora que se encarga de procesar las peticiones asíncronas desde Javascript
@RestController
public class HomeController {

	// METODOS USUARIO

	@Autowired
	private UsuarioService servicioUsuarios;

	// Método que devuelve toda la información de los usuarios de la base de datos
	@GetMapping(value = "/showUsers")
	public List<Usuario> verUsuarios() {
		return servicioUsuarios.getAll();
	}

	// Método que devuelve la lista de usuarios ordenada por media de puntuacion por partida (descendente)
	@GetMapping(value = "/ranking")
	public List<Usuario> mostrarRanking() {
		List<Usuario> usuarios = servicioUsuarios.getAll();
		usuarios.sort(new Comparator<Usuario>() {
			@Override
			public int compare(Usuario u1, Usuario u2) {
				return (int) (u2.getMedia() - u1.getMedia());
			}
		});
		return usuarios;
	}

	// Método que devuelve el historial de partidas de un usuario
	@GetMapping(value = "/history")
	public Object[][] mostrarHistorial(@RequestParam(name = "idUsuario") int idUsuario) {
		// Obtener el usuario por su id
		Usuario usuario = servicioUsuarios.getUser(idUsuario).orElse(null);
		// Partidas que ha jugado el usuario
		Map<Partida, Integer> partidas = usuario.getPartidas();
		// Convertir el mapa a una lista de entradas
		List<Map.Entry<Partida, Integer>> listaPartidas = new ArrayList<>(partidas.entrySet());
		// Ordenar la lista según el idPartida de cada entrada
		Collections.sort(listaPartidas, Comparator.comparing(entry -> entry.getKey().getIdPartida()));		
		// Jugadores que han participado en las partidas
		List<Usuario> jugadores = servicioUsuarios.getAll();
		Object[][] resultados = new Object[partidas.size()][4];
		// Obtener los datos de cada partida 
		int i = 0;
		for (Map.Entry<Partida, Integer> e : listaPartidas) {
			Partida partidaUsuario = e.getKey();
			Integer puntosUsuario = e.getValue();			
	        Integer contadorJugadores = 0;	        			
			ArrayList<Integer> puntuaciones = new ArrayList<Integer>();// Puntuciones de cada jugador
			for (Usuario jugador : jugadores) {
				// Verificar si este jugador tiene la misma partida que el usuario
				if (jugador.getPartidas().containsKey(partidaUsuario)) {
					contadorJugadores++;
					// Obtener los puntos de este jugador en la misma partida
					puntuaciones.add(jugador.getPartidas().get(partidaUsuario));
				}
			}
			// Ordenar las puntuaciones de forma descendente
			Collections.sort(puntuaciones, Collections.reverseOrder());
			// Obtener la posición del usuario en las puntuaciones ordenadas
			Integer posicionUsuario = puntuaciones.indexOf(puntosUsuario);
	        // Almacenar los datos en la matriz de resultados
	        resultados[i][0] = partidaUsuario.getFechaPartida();
	        resultados[i][1] = puntosUsuario;
	        resultados[i][2] = contadorJugadores;
	        resultados[i][3] = posicionUsuario+1+"ª";
	        i++;
		}
		return resultados;
	}

	// Método que modifica el avatar de un usuario en la base de datos
	@PostMapping(value = "/modAvatar")
	public void modificarAvatar(@RequestParam(name = "avatar") int avatar,
								@RequestParam(name = "idUsuario") int idUsuario) {
		Usuario usuario = servicioUsuarios.getUser(idUsuario).orElse(null);
		usuario.setAvatar(avatar);
		servicioUsuarios.addUser(usuario);
	}

	// Método que elimina usuarios de la base de datos
	@PostMapping(value = "/deleteUser")
	public List<Usuario> eliminarUsuarios(@RequestParam(name = "usernames") ArrayList<String> usernames) {
		for (Usuario usuario : servicioUsuarios.getAll()) {
			if (usernames.contains(usuario.getNombreUsuario())) {
				servicioUsuarios.deleteUser(usuario.getIdUsuario());
				SecurityConfiguration.inMemory.deleteUser(usuario.getNombreUsuario());
			}
		}
		return servicioUsuarios.getAll();
	}
	
	// Método que modifica el rol de un usuario en la base de datos	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/modRolUser")
	public void modRolUsuario(@RequestParam(name = "username") String username,
							  @RequestParam(name = "idRol") int idRol) {
		for (Usuario usuario : servicioUsuarios.getAll()) {
			// Si el nombre de usuario es igual al proporcionado, se actualiza su idRol con el nuevo valor.
			if (usuario.getNombreUsuario().equals(username)) {
				usuario.setIdRol(idRol);
				servicioUsuarios.addUser(usuario);
				// Actualización en la Configuración de Seguridad
				String rol = usuario.getRol().getNombre();
				SecurityConfiguration.inMemory.updateUser(
						User.withDefaultPasswordEncoder().username(username).roles(rol).build());
				break;
			}
		}
	}

	// METODOS PALABRA

	@Autowired
	private PalabraService palabraservice;

	// Método que añade palabras al diccionario
	@PostMapping(value = "/addWord")
	public String insertarPalabra(@RequestParam(name = "palabra") String palabra) {
		if (palabraservice.addWord(new Palabra(palabra))) {
			return "Se ha añadido la palabra " + palabra;
		} else {
			return "No se ha podido añadir la palabra";
		}
	}

	// Método que devuelve las palabras del diccionario
	@GetMapping(value = "/showWords")
	public List<Palabra> verPalabras() {
		return palabraservice.getAll();
	}

	// Método que elimina palabras del diccionario
	@PostMapping(value = "/deleteWord")
	public String eliminarPalabras(@RequestParam(name = "palabras") ArrayList<String> palabras) {
		int eliminadas = 0;
		for (Palabra palabra : palabraservice.getAll()) {
			if (palabras.contains(palabra.getPalabra())) {
				palabraservice.deleteWord(palabra.getId());
				eliminadas++;
			}
		}
		return "Número de palabras eliminadas: " + eliminadas;
	}
}
