package es.urjc.computadores.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
/*import org.springframework.data.jpa.repository.Query;

import java.util.List;*/

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	Usuario findByNombreInterno(String nombreInterno);
	
	//@Query("SELECT * FROM usuario_roles WHERE roles != ADMIN")
	//List<Usuario> findAllUsuariosSinAdmin();
}
