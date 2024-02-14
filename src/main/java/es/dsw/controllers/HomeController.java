package es.dsw.controllers;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import es.dsw.models.Palabra;
import es.dsw.models.Usuario;
import es.dsw.services.PalabraService;
import es.dsw.services.UsuarioService;

//Controladora que se encarga de procesar las peticiones asíncronas desde Javascript
@RestController
public class HomeController {
	
	// METODOS USUARIO
	
	@Autowired 
	private UsuarioService servicioUsuarios;
	
	//Método que devuelve toda la información de los usuarios de la base de datos
	@GetMapping(value = "/showUsers")
	public List<Usuario> verUsuarios(){
		return servicioUsuarios.getAll();
	}	
	//Método que devuelve la lista de usuarios con su puntuacion acumulada en orden descendente
	@GetMapping(value = "/stats")
	public List<Usuario> mostrarEstadisticas(){
		List<Usuario> usuarios = servicioUsuarios.getAll();
        usuarios.sort(new Comparator<Usuario>() {
            @Override
            public int compare(Usuario u1, Usuario u2) {
                return u2.getAcumulado() - u1.getAcumulado();
            }
        });		
		return usuarios; 
	}
	
	//Método que modifica el avatar de un usuario en la base de datos
	@PostMapping(value = "/modAvatar")
	public void modificarAvatar(@RequestParam(name = "avatar") int avatar,
								@RequestParam(name = "idUsuario") int idUsuario){
		Usuario user = servicioUsuarios.getUser(idUsuario).orElse(null);
		user.setAvatar(avatar);
		servicioUsuarios.addUser(user);
	}
	
	//Método que elimina usuarios de la base de datos
	@PostMapping(value = "/deleteUser")
	public List<Usuario> eliminarUsuarios(@RequestParam(name = "usernames") ArrayList<String> usernames) {		
        for (Usuario e : servicioUsuarios.getAll()) {
            if (usernames.contains(e.getNombreUsuario())) {
            	servicioUsuarios.deleteUser(e.getIdUsuario());
            }
        }
		return servicioUsuarios.getAll();
	}

	
	// METODOS PALABRA	
	
	@Autowired 
	private PalabraService palabraservice;
	
	//Método que añade palabras al diccionario
	@PostMapping(value = "/addWord")
	public String insertarPalabra(@RequestParam(name = "palabra")String palabra) {
		if(palabraservice.addWord(new Palabra(palabra))) {
			return "Se ha añadido la palabra "+palabra;
		} else {
			return "No se ha podido añadir la palabra";
		}	
	}
	
	//Método que devuelve las palabras del diccionario
	@GetMapping(value = "/showWords")
	public List<Palabra> verPalabras(){
		return palabraservice.getAll();
	}
	
	//Método que elimina palabras del diccionario
	@PostMapping(value = "/deleteWord")
	public String eliminarPalabras(@RequestParam(name = "palabras") ArrayList<String> palabras) {
		int eliminadas = 0;
        for (Palabra e : palabraservice.getAll()) {
            if (palabras.contains(e.getPalabra())) {
            	palabraservice.deleteWord(e.getId());
            	eliminadas++;
            }
        }
		return "Número de palabras eliminadas: " + eliminadas;
	}
}
