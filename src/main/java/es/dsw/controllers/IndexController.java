package es.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import es.dsw.SecurityConfiguration;
import es.dsw.models.Partida;
import es.dsw.models.Usuario;
import es.dsw.services.PartidaService;
import es.dsw.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//Controladora que se encarga de dirigir al usuario a las diferentes vistas de la aplicación
@Controller
@SessionAttributes({ "usuario" })
public class IndexController {
	@Autowired
	private UsuarioService servicioUsuarios;
	@Autowired
	private PartidaService partidaService;

	// Método que devuelve la vista de inicio de sesión y registro
	@GetMapping(value = { "/", "/index" })
	public String index() {
		return "index";
	}

	// Método que devuelve la vista del menú principal
	@GetMapping(value = "/home")
	public String home(Model model, Authentication authentication) {
		Usuario usuario = new Usuario();
		for (Usuario e : servicioUsuarios.getAll()) {
			if (e.getNombreUsuario().equals(authentication.getName())) {
				usuario = e;
				model.addAttribute("usuario", usuario);
				break;
			}
		}
		return "home";
	}

	// Vista de inicio de sesión
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}

	// Vista de registro de usuarios
	@GetMapping(value = "/register")
	public String register() {
		return "register";
	}

	// Método que añade a la base de datos los usuarios registrados
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/addUser")
	public String aniadirUsuario(@RequestParam(name = "username", defaultValue = "") String nombreUsuario,
			@RequestParam(name = "password", defaultValue = "") String contra,
			@RequestParam(name = "name", defaultValue = "") String nombre,
			@RequestParam(name = "surnames", defaultValue = "") String apellidos,
			@RequestParam(name = "email", defaultValue = "") String email, HttpServletRequest request,
			HttpServletResponse response) {

		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		SecurityContextHolder.clearContext();

		Usuario user = new Usuario(nombreUsuario, contra, nombre, apellidos, email);
		boolean aniadido = servicioUsuarios.addUser(user);
		if (!aniadido) {
			return "redirect:/register?error";
		}
		SecurityConfiguration.inMemory.createUser(
				User.withDefaultPasswordEncoder().username(nombreUsuario).password(contra).roles("jugador").build());
		return "redirect:/index";
	}

	// Vista de la partida
	@GetMapping(value = "/game")
	public String juego(@RequestParam(name = "code", defaultValue = "0") int codigo,
						@RequestParam(name = "idUser", defaultValue = "0") int idUsuario,	
						Model model) {
		Partida partida = new Partida();
		String mensaje = "La partida no existe";
		// Unirse a partida privada
		if (codigo != 0) {
			for (Partida e : partidaService.getAll()) {
				if (e.getEstado().equals("creada") && e.getCodPrivada() == codigo) {
					partida = e;
					mensaje="";
					break;
				} 
			}
			if (!mensaje.equals("")){
				return "redirect:/home";
			}
		// Unirse a partida publica	
		} else {
			for (Partida e : partidaService.getAll()) {
				if (e.getEstado().equals("creada") && e.getCodPrivada() == 0) {
					partida = e;
					mensaje="";
					break;
				} 
			}
			if (!mensaje.equals("")){
				partidaService.addGame(partida);
			}
		}
		// Asociar la partida con el usuario
		Usuario usuario = servicioUsuarios.getUser(idUsuario).orElse(null);		
		usuario.setPartidaActual(partida,0);
		servicioUsuarios.addUser(usuario);
		
		return "juego";
	}
}
