package es.dsw.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import es.dsw.models.Partida;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {

}
