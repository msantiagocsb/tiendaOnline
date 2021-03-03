package es.urjc.computadores.pedido;

import es.urjc.computadores.producto.Producto;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	Pedido findByProducto(Producto p);
}
