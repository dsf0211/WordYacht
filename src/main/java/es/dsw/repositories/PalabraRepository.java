package es.dsw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.dsw.models.Palabra;

public interface PalabraRepository extends JpaRepository<Palabra, Integer>{

}
