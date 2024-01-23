package es.dsw.services;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dsw.models.Usuario;
import es.dsw.repositories.UsuarioRepository;

@Service
public class UsuarioService {
	@Autowired
	private UsuarioRepository repositorioUsuarios;
	
	public List<Usuario> GetAll(){
		return repositorioUsuarios.findAll();
	}
	
	public boolean addUser(Usuario user) {
		try {
		repositorioUsuarios.save(user);
		} catch (Exception e) {
			return false; 
		}
		return true;
	}
	
	public int GetLastId() {
		List<Usuario> lista = repositorioUsuarios.findAll();
		return lista.get(lista.size() - 1).getIdUsuario() + 1;
	}
}
