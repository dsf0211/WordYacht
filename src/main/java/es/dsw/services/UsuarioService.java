package es.dsw.services;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dsw.models.Usuario;
import es.dsw.repositories.UsuarioRepository;

//Clase que gestiona el repositorio de usuarios de la base de datos
@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repositorioUsuarios;
	
	//Método que devuelve todos los usuarios
	public List<Usuario> getAll(){
		return repositorioUsuarios.findAll();
	}
	
	//Método que inserta o modifica usuarios en la base de datos
	public boolean addUser(Usuario user) {
		try {
		repositorioUsuarios.save(user);
		} catch (Exception e) {
			return false; 
		}
		return true;
	}
	
	//Método que devuelve un usuario según su id
	public Optional<Usuario> getUser(int id){
		return repositorioUsuarios.findById(id);
	}
	
	//Método que elimina un usuario según su id
	public void deleteUser(int id){
		repositorioUsuarios.deleteById(id);
	}
}
