package es.dsw.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dsw.models.Partida;
import es.dsw.repositories.PartidaRepository;

@Service
public class PartidaService {
	@Autowired
	private PartidaRepository partidaRepository;

	//Método que devuelve todas las partidas de la base de datos
	public List<Partida> getAll(){
		return partidaRepository.findAll();
	}
	
	//Método que inserta o modifica partidas en la base de datos
	public boolean addGame(Partida partida) {
		try {
			partidaRepository.save(partida);
		} catch (Exception e) {
			return false; 
		}
		return true;
	}
	// Método que devuelve una partida segun su id
	public Optional<Partida> getGame(int id) {
		return partidaRepository.findById(id);
	}
	

}
