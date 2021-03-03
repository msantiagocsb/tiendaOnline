package es.urjc.computadores.usuario;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseUsersLoader {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@PostConstruct
	private void initDataBase() {
		if(usuarioRepository.findByNombreInterno("admin") == null) {
			usuarioRepository.save(new Usuario("admin", "1234", "admin", "proyecto@gmail.com", true));
		}
	}

}
