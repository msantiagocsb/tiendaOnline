package es.urjc.computadores.usuario;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.chat.Chat;
import es.urjc.computadores.chat.ChatRepository;
import es.urjc.computadores.pedido.Pedido;
import es.urjc.computadores.pedido.PedidoRepository;
import es.urjc.computadores.producto.ProductoRepository;

@Controller
public class UsuarioController implements CommandLineRunner {

	@Autowired
	private UsuarioRepository usuarioRepo;
	
	@Autowired
	private PedidoRepository pedidoRepo;
	
	@Autowired
	private ProductoRepository productoRepo;
	
	@Autowired
	private ChatRepository chatRepo;
	
	@Autowired
	private UserComponent usuariologueado;

	@Override
	public void run(String... args) throws Exception {

	}

	@PostConstruct
	public void init() {

	}

	@RequestMapping("/registrar")
	public String SignUp(Model model) {
		return "SignUp";
	}

	@PostMapping("/inputuser")
	public String insertarDato(Model model, @RequestParam String nombreRealIntroducido, String nombreInternoIntroducido,
			String correoIntroducido, String claveIntroducido) {
		usuarioRepo.save(new Usuario(nombreRealIntroducido, new BCryptPasswordEncoder().encode(claveIntroducido),
				nombreInternoIntroducido, correoIntroducido));
		return "registrar";
	}

	@GetMapping("/login")
	public String SignIn(Model model) {
		return "SignIn";
	}

	@GetMapping("/listausuarios")
	public String listarUsuarios(Model model) {
		List<Usuario> lista = usuarioRepo.findAll();
		int index = 0;
		for(Usuario u : lista){
			if(u.getNombreInterno().equals("admin")) {
				break;
			}
			index++;
		}
		lista.remove(index);
		model.addAttribute("datos", lista);
		return "usuarios";
	}

	@GetMapping("/usuario/{userid}")
	public String verDatosUsuario(Model model, @PathVariable Long userid, HttpServletRequest request) {
		Usuario elegido = usuarioRepo.findById(userid).get();
		model.addAttribute("usuario", elegido);
		model.addAttribute("productos", elegido.getProductosEnVenta());
		model.addAttribute("user", usuariologueado.getLoggedUser());
		//comprobacion 
		if((usuariologueado.getLoggedUser() != null && usuariologueado.getLoggedUser().getId()==userid) || (request.isUserInRole("ADMIN"))) {
			model.addAttribute("espropietario", usuariologueado);
		}
		
		
		return "usuario";
	}

	@RequestMapping(value= "/loginerror", method = RequestMethod.POST)
	public String SignInError(Model model) {
		return "loginerror";
	}
	
	@GetMapping("/deleteUsuario/{userid}")
	public String DeleteUsuario(Model model, @PathVariable Long userid) {
		Usuario eliminado = usuarioRepo.findById(userid).get();
//		List<Chat> listaChat = chatRepo.findByComprador(eliminado);
//		listaChat.addAll(chatRepo.findByVendedor(eliminado));
//		for(Chat c : listaChat) chatRepo.delete(c);
//		List<Pedido> = pedidoRepo.findByPropietario(eliminado);
		usuarioRepo.delete(eliminado);
		
		List<Usuario> lista = usuarioRepo.findAll();
		int index = 0;
		for(Usuario u : lista){
			if(u.getNombreInterno().equals("admin")) {
				break;
			}
			index++;
		}
		lista.remove(index);
		model.addAttribute("datos",lista);
		
		return "usuarios";
	}
}
