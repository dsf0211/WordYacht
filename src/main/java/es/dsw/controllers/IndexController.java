package es.dsw.controllers;

import java.util.HashSet;
import java.util.Set;
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

	// Almacenar códigos existentes de partidas privadas que aún no se han iniciado
	public Set<Integer> codigosPartidasPrivadas() {
		Set<Integer> codigos = new HashSet<>();
		for (Partida partida : partidaService.getAll()) {
			if (partida.getEstado().equals("creada") && partida.getCodPrivada() != 0) {
				codigos.add(partida.getCodPrivada());
			}
		}
		return codigos;
	}

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
					break;
				}
			}
			model.addAttribute("codigos", codigosPartidasPrivadas());
			model.addAttribute("usuario", usuario);
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

		//Cerrar sesión
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
		logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
		SecurityContextHolder.clearContext();

		//Añadir usuario a la base de datos 
		Usuario user = new Usuario(nombreUsuario, contra, nombre, apellidos, email);
		boolean aniadido = servicioUsuarios.addUser(user);
		if (!aniadido) {
			//Mensaje de error si no se realizó la inserción correctamente
			return "redirect:/register?error";
		}
		//Actualización en la Configuración de Seguridad
		SecurityConfiguration.inMemory.createUser(
				User.withDefaultPasswordEncoder().username(nombreUsuario).password(contra).roles("jugador").build());
		return "redirect:/index";
	}
}
