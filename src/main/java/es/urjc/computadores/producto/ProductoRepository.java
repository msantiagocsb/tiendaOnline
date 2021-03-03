package es.urjc.computadores.producto;


import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import es.urjc.computadores.usuario.Usuario;


@CacheConfig(cacheNames="cache_marketplace")

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	@Cacheable
	List<Producto> findByPropietario(Usuario u);
	@Cacheable
	List<Producto> findAll();
	
	
	@CacheEvict(allEntries = true)
	Producto save(Producto producto);
	
	
	
}
