package es.urjc.computadores.producto;

import java.io.Serializable;

import javax.persistence.*;

import es.urjc.computadores.pedido.Pedido;
import es.urjc.computadores.usuario.Usuario;

@Entity
public class Producto implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private double precio;
	private String categoria;
	private String descripcion;
	private String titulo;
	@ManyToOne
	private Usuario propietario;
	
	@OneToOne(cascade = CascadeType.ALL)
	private Pedido pedido;

	public Producto() {}
	
	public Producto(double precio,String categoria,String titulo, String descripcion, Usuario propietario) {
		this.precio=precio;
		this.categoria=categoria;
		this.titulo=titulo;
		this.descripcion=descripcion;
		this.propietario=propietario;
	}

	public long getId() {
		return id;
	}
	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	@Override
	public String toString() {
		return titulo+"-"+descripcion+"--"+categoria+"--"+precio+"--"+propietario.getNombreReal(); 
	}
	
	public boolean equals(Producto p) {
		return this.id==p.getId();
	}
	public int hashCode() {
		return (int)id;
		
		
	}
	
	
}
