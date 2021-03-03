package es.urjc.computadores.chat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import es.urjc.computadores.mensaje.Mensaje;
import es.urjc.computadores.producto.Producto;
import es.urjc.computadores.usuario.Usuario;


@Entity
public class Chat implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	
	@ManyToOne
	private Usuario comprador;
	
	@ManyToOne 
	private Usuario vendedor;
	
	@OneToMany(cascade=CascadeType.ALL)
	private List<Mensaje> mensajes;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Producto producto;
	
	private String titulo;
	
	protected Chat() {}
	
	public Chat(Usuario comprador, Usuario vendedor,Producto producto) {
		this.comprador = comprador;
		this.vendedor = vendedor;
		this.producto=producto;
		this.titulo=producto.getTitulo();
		mensajes = new ArrayList<Mensaje>();
	}

	public List<Mensaje> getMensajes() {
		return mensajes;
	}
	
	public void insertarMensaje(String mensaje) {
		this.mensajes.add(new Mensaje(mensaje, new Date()));
	}

	public long getId() {
		return id;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public Usuario getVendedor() {
		return vendedor;
	}
	public Producto getProduct() {
		return producto;
	}

	public Producto getProducto() {
		return producto;
	}

	public String getTitulo() {
		return titulo;
	}
	
	
	
	
	
}
