package es.dsw.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.dsw.repositories.RolRepository;

@Service
public class RolService {
	@Autowired
	private RolRepository rolRepository;
	
	public String getRoleById(int id){
		return rolRepository.findById(id).get().getNombre();
	}
}
