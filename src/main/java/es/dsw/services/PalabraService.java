package es.dsw.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dsw.models.Palabra;
import es.dsw.repositories.PalabraRepository;

//Clase que gestiona el repositorio de palabras de la base de datos
@Service
public class PalabraService {
	@Autowired
	private PalabraRepository palabraRepository;
	
	//Método que devuelve todas las palabras de la base de datos
	public List<Palabra> getAll(){
		return palabraRepository.findAll();
	}
	
	//Método que inserta o modifica palabras en la base de datos
	public boolean addWord(Palabra palabra) {
		try {
			palabraRepository.save(palabra);
		} catch (Exception e) {
			return false; 
		}
		return true;
	}
	
	//Método que elimina una palabra según su id
	public void deleteWord(int id){
		palabraRepository.deleteById(id);
	}
	
}
