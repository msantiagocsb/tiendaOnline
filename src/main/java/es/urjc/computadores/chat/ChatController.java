package es.urjc.computadores.chat;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.chat.Chat;
import es.urjc.computadores.mensaje.Mensaje;
import es.urjc.computadores.producto.Producto;
import es.urjc.computadores.producto.ProductoRepository;
import es.urjc.computadores.usuario.UserComponent;
import es.urjc.computadores.usuario.Usuario;
import es.urjc.computadores.usuario.UsuarioRepository;
import es.urjc.computadores.chat.ChatRepository;

@Controller
public class ChatController implements CommandLineRunner {

	@Autowired
	private ChatRepository chatRepo;
	@Autowired
	private UsuarioRepository usuarioRepo;
	@Autowired
	private ProductoRepository productoRepo;
	@Autowired
	private UserComponent usuario;

	@Override
	public void run(String... args) throws Exception {

	}

	@PostConstruct
	public void init() {

	}

	@PostMapping("/inputchat")
	public String insertarChat(Model model, @RequestParam String idpropietario, String idproducto) {
		Usuario propietario = usuarioRepo.findById(Long.parseLong(idpropietario)).get();
		Usuario comprador = usuarioRepo.findById(usuario.getLoggedUser().getId()).get();
		List<Chat> chatsUsuario = chatRepo.findByComprador(comprador);

		System.out.println(chatsUsuario);
		Producto p = productoRepo.findById(Long.parseLong(idproducto)).get();
		boolean existe = false;
		for (Chat c : chatsUsuario) {
			existe = c.getTitulo().equals(p.getTitulo()) && (c.getComprador().getId() == usuario.getLoggedUser().getId()) && (c.getVendedor().getId() == p.getPropietario().getId());
			if(existe) break;
		}
		if (!existe) {
			Chat chat = new Chat(comprador, propietario, p);
			chatRepo.save(chat);
		}

		model.addAttribute("user", usuario.getLoggedUser());
		model.addAttribute("nombreUser", usuario.getLoggedUser().getNombreReal());

		List<Chat> listaChat = chatRepo.findByComprador(comprador);
		listaChat.addAll(chatRepo.findByVendedor(comprador));
		model.addAttribute("datos", listaChat);
		model.addAttribute("userid", usuario.getLoggedUser().getId());
		model.addAttribute("producto", p);

		return "listachats";
	}

	@GetMapping("/{userid}/chats")
	public String verChatDeUsuario(Model model, @PathVariable Long userid) {
		Usuario elegido = usuarioRepo.findById(userid).get();
		List<Chat> listaChat = chatRepo.findByComprador(elegido);
		listaChat.addAll(chatRepo.findByVendedor(elegido));
		model.addAttribute("datos", listaChat);
		model.addAttribute("userid", userid);

		return "listachats";
	}

	@RequestMapping("/chats/{id}")
	public String verChat(Model model, @PathVariable Long id) {
		Chat elegido = chatRepo.findById(id).get();
		List<Mensaje> mensajes = elegido.getMensajes();

		model.addAttribute("mensajes", mensajes);
		model.addAttribute("userid", elegido.getVendedor().getId());
		model.addAttribute("chatid",id);
		model.addAttribute("titulo", elegido.getTitulo());

		return "chat";
	}

}
