package es.dsw.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "diccionario")
public class Palabra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_palabra")
	private int id;
	@Column(name = "palabra")
	private String palabra;
	
	public Palabra() {
	}	
	
	public Palabra(String palabra) {
		this.palabra = palabra;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPalabra() {
		return palabra;
	}
	public void setPalabra(String palabra) {
		this.palabra = palabra;
	}
	
}
