package es.dsw.models;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@SuppressWarnings("serial")
@Embeddable
public class idRolUsuario implements Serializable {

    @Column(name = "id_usuario_ur")
    private int idUsuario;

    @Column(name = "id_rol_ur")
    private int idRol;
  
	public idRolUsuario(int idUsuario, int idRol) {
		this.idUsuario = idUsuario;
		this.idRol = idRol;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdRol() {
		return idRol;
	}

	public void setIdRol(int idRol) {
		this.idRol = idRol;
	}
}
