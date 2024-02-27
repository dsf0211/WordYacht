package es.dsw.models;

import java.util.Random;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	
	@Column(name = "letras")
	private String letras;

	public Partida() {
		this.estado = "creada";
		this.codPrivada = 0;
        this.letras = generarLetras();
	}

	//Método que genera 8 listas (para 8 rondas) de 10 letras aleatorias comunes a todos los jugadores de la partida
	public String generarLetras() {
		String vocales = "AEIOU";
		String alfabeto = "ABBCCDDEFFGGHIJKLLMMNÑOPPQRRSSTTUVWXYZ";
		String listaLetras = "";
		Random random = new Random();
		// Generar 10 letras aleatorias por 8 rondas
		for (int i = 0; i < 8; i++) {
			String letras = "";
			// Generar 3 vocales aleatorias
			for (int j = 0; j < 3; j++) {
				char vocal = vocales.charAt(random.nextInt(vocales.length()));
				letras+=vocal;
			}
			// Generar 7 letras del alfabeto aleatorias
			for (int k = 0; k < 7; k++) {
				char letra = alfabeto.charAt(random.nextInt(alfabeto.length()));
				letras+=letra;
			}
			listaLetras+=letras+",";
		}
		return listaLetras.substring(0, listaLetras.length()-1);
	}

	public String getLetras() {
		return letras;
	}

	public void setLetras(String letras) {
		this.letras = letras;
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
