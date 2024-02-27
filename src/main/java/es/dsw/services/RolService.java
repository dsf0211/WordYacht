package es.dsw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dsw.models.Rol;
import es.dsw.repositories.RolRepository;

@Service
public class RolService {
	
	@Autowired
	private RolRepository rolRepository;
	
	//Método que devuelve todos los roles de la base de datos
	public List<Rol> getAll(){
		return rolRepository.findAll();
	}
	
	//Método que devuelve un rol según su id
	public Optional<Rol> getRole(int id){		
		return rolRepository.findById(id);
	}
	
	//Método que inserta o modifica roles en la base de datos
	public boolean addRole(Rol rol) {
		try {
			rolRepository.save(rol);
		} catch (Exception e) {
			return false; 
		}
		return true;
	}
	
	//Método que elimina un rol según su id
	public void deleteRole(int id){
		rolRepository.deleteById(id);
	}

}
