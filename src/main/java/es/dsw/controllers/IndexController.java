package es.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.dsw.SecurityConfiguration;
import es.dsw.models.Usuario;
import es.dsw.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
	@Autowired 
	private UsuarioService servicioUsuarios;
	@GetMapping(value = {"/","/index"})
	public String index() {
		return "index";
	}
	
	@GetMapping(value = "/login")
	public String login() {
		return "login";
	}
	
	@GetMapping(value = "/register")
	public String register() {
		return "register";
	}
	
	@SuppressWarnings("deprecation")
	@PostMapping(value = "/addUser")
	public String aniadirUsuario(
			@RequestParam(name = "username",defaultValue = "") String nombreUsuario,
			@RequestParam(name = "password", defaultValue = "") String contra,
			@RequestParam(name = "name", defaultValue = "") String nombre,
			@RequestParam(name = "surnames", defaultValue = "") String apellidos,
			@RequestParam(name = "email", defaultValue = "") String email,
			HttpServletRequest request,
			HttpServletResponse response
			) {
		
		SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        SecurityContextHolder.clearContext(); 
        
    	int idUsuario = servicioUsuarios.GetLastId();
        Usuario user = new Usuario(idUsuario, nombreUsuario, contra, nombre, apellidos, email);
		boolean aniadido = servicioUsuarios.addUser(user);
		if (!aniadido) {
			return "redirect:/register?error";
		}
		SecurityConfiguration.inMemory.createUser(User.withDefaultPasswordEncoder().username(nombreUsuario).password(contra).build());
        return "redirect:/index";
	}
}
