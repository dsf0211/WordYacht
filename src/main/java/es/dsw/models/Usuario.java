package es.dsw.models;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private int idUsuario;
	@Column(name = "username")
	private String nombreUsuario;
	@Column(name = "password")
	private String contra;
	@Column(name = "nombre")
	private String nombre;
	@Column(name = "apellidos")
	private String apellidos;
	@Column(name = "email")
	private String email;
	@Column(name = "insert_date", insertable = false)
	private String fecha_registro;
	@Column(name = "puntos_acum")
	private int acumulado;
	@Column(name = "avatar")
	private int avatar;
	@Column(name = "id_rol_us", insertable = false)
	private int idRol;

	@ManyToOne
	@JoinColumn(name = "id_rol_us", insertable = false, updatable = false)
	private Rol rol;

	@ElementCollection
	@CollectionTable(name = "usuario_partida", joinColumns = {
			@JoinColumn(name = "id_usuario_up", referencedColumnName = "id_usuario") })
	@MapKeyJoinColumn(name = "id_partida_up")
	@Column(name = "puntos")
	private Map<Partida, Integer> partidas = new HashMap<>();

	@Transient
	private String[] palabras;
	@Transient
	private String categoria;	
	@Transient
	private int puntos;
	
	public Usuario() {

	}

	public Usuario(String nombreUsuario, String contra, String nombre, String apellidos, String email) {
		this.nombreUsuario = nombreUsuario.equals("") ? null : nombreUsuario;
		this.contra = contra.equals("") ? null : contra;
		this.nombre = nombre.equals("") ? null : nombre;
		this.apellidos = apellidos;
		this.email = email;
	}

	// Metodo para unirse a una partida
	public void asociarAPartida(Partida partida) {
		this.partidas.put(partida, 0);
	}

	// Metodo que modifica los puntos del usuario en una partida
	public void actualizarPuntos(Partida partida, int puntos) {
		this.partidas.put(partida, puntos);
	}

	public int getPuntos() {
		return puntos;
	}

	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}

	public int getIdRol() {
		return idRol;
	}

	public String getFecha_registro() {
		return fecha_registro;
	}

	public void setFecha_registro(String fecha_registro) {
		this.fecha_registro = fecha_registro;
	}

	public int getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(int acumulado) {
		this.acumulado = acumulado;
	}

	public int getAvatar() {
		return avatar;
	}

	public void setAvatar(int avatar) {
		this.avatar = avatar;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		this.contra = contra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<Partida, Integer> getPartidas() {
		return partidas;
	}

	public void setPartidas(Map<Partida, Integer> partidas) {
		this.partidas = partidas;
	}

	public String[] getPalabras() {
		return palabras;
	}

	public void setPalabras(String[] palabras) {
		this.palabras = palabras;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

}
