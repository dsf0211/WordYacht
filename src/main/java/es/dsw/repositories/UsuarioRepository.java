package es.dsw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.dsw.models.Usuario;

//Interfaz que contiene el repositorio de usuarios de la base de datos
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{	
	
}
