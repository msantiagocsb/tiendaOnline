package es.urjc.computadores;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.urjc.computadores.pedido.Pedido;
import es.urjc.computadores.pedido.PedidoRepository;
import es.urjc.computadores.producto.*;
import es.urjc.computadores.usuario.*;

@Controller
public class GreetingController implements CommandLineRunner {

	@Autowired
	private ProductoRepository productoRepo;
	@Autowired
	private PedidoRepository pedidoRepo;


	@Autowired
	private UserComponent usuario;

	@Override
	public void run(String... args) throws Exception {

	}

	@PostConstruct
	public void init() {

	}

	@GetMapping("/")
	public String greeting(Model model, HttpServletRequest request) {
		List<Producto> lista = productoRepo.findAll();
		List<Pedido> pedidos = pedidoRepo.findAll();
		List<Producto> productosAux= new ArrayList<Producto>();
		for(Pedido p: pedidos) {
			if(lista.contains(p.getProducto())){
			
					lista.remove(p.getProducto());
			}
		}
		
		if (usuario.getLoggedUser() != null) {
			/*ArrayList<Integer> aux= new ArrayList<Integer>();
			int indice=0;
			for(Producto p: lista) {
				if(p.getPropietario().getNombreReal().equals(usuario.getLoggedUser().getNombreReal())) {
					aux.add(indice-1);
				}
				indice++;
			}
			for(int a: aux) {
				lista.remove(a);
			
			}
		*/
			List<Producto> productosUser = productoRepo.findByPropietario(usuario.getLoggedUser());
			lista.removeAll(productosUser);
			
			model.addAttribute("user", usuario.getLoggedUser());
			model.addAttribute("nombreUser", usuario.getLoggedUser().getNombreReal());
			if(request.isUserInRole("ADMIN")) {
				model.addAttribute("admin",usuario.getLoggedUser());
			}
		}
		model.addAttribute("productos", lista);
		return "main";
	}

}
