package es.dsw.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "partida")
public class Partida {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_partida")
	private int idPartida;

	@Column(name = "fecha", insertable = false)
	private String fechaPartida;

    @Column(name = "estado")
    private String estado;	

	@Column(name = "cod_privada")
	private int codPrivada;

 
    
	public Partida() {
		this.estado = "creada";
		this.codPrivada = 0;
	}
	
	public int getIdPartida() {
		return idPartida;
	}

	public void setIdPartida(int idPartida) {
		this.idPartida = idPartida;
	}

	public int getCodPrivada() {		
		return codPrivada;
	}

	public void setCodPrivada(int codPrivada) {
		this.codPrivada = codPrivada;
	}

	public String getFechaPartida() {
		return fechaPartida;
	}

	public void setFechaPartida(String fechaPartida) {
		this.fechaPartida = fechaPartida;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}



}
