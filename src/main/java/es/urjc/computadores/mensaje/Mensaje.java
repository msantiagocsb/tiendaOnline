package es.urjc.computadores.mensaje;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

@Entity
public class Mensaje implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String cuerpo;
	private Date fecha;
	
	protected Mensaje(){}
	
	public Mensaje(String mensaje, Date fecha) {
		this.cuerpo = mensaje;
		this.fecha = fecha;
	}

	public String getCuerpo() {
		return cuerpo;
	}

	public Date getFecha() {
		return fecha;
	}

	public long getId() {
		return id;
	}

	
}
