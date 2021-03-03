package es.urjc.computadores.producto;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

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
import es.urjc.computadores.chat.ChatRepository;
import es.urjc.computadores.pedido.Pedido;
import es.urjc.computadores.pedido.PedidoRepository;
import es.urjc.computadores.usuario.UserComponent;
import es.urjc.computadores.usuario.Usuario;

@Controller
public class ProductoController implements CommandLineRunner {

	@Autowired
	private ProductoRepository productoRepo;

	@Autowired
	private PedidoRepository pedidoRepo;

	@Autowired
	private ChatRepository chatRepo;
	
	@Autowired
	private UserComponent usuario;

	@Override
	public void run(String... args) throws Exception {

	}

	@PostConstruct
	public void init() {

	}

	@PostMapping("/inputproducto")
	public String insertarProducto(Model model, @RequestParam String precioIntroducido, String tituloIntroducido,
			String categoriaIntroducido, String descripcionIntroducido, Long id) {
		if(id==null) {
			Producto p1 = new Producto(Double.parseDouble(precioIntroducido), categoriaIntroducido, tituloIntroducido,
					descripcionIntroducido, usuario.getLoggedUser());
			productoRepo.save(p1);
			
		} else {
			Producto p = productoRepo.findById(id).get();
			p.setCategoria(categoriaIntroducido);
			p.setDescripcion(descripcionIntroducido);
			p.setPrecio(Double.parseDouble(precioIntroducido));
			p.setTitulo(tituloIntroducido);
			productoRepo.save(p);
		}
		model.addAttribute("id", usuario.getLoggedUser().getId());
		return "subirproducto";
		
	}

	@GetMapping("/producto/{num}")
	public String verProducto(Model model, @PathVariable Long num) {
		Producto elegido = productoRepo.findById(num).get();
		model.addAttribute("producto", elegido);
		if(usuario.getLoggedUser() != null) {
			model.addAttribute("user", usuario.getLoggedUser());
		}		
		return "producto";
	}

	@GetMapping("/subirproducto")
	public String subirProducto(Model model) {
		model.addAttribute("id", usuario.getLoggedUser().getId());
		return "subirproducto";
	}
	
	@GetMapping("/modificarproducto")
	public String modificarProducto(Model model, @RequestParam Long productid) {
		Producto prod = productoRepo.findById(productid).get();
		model.addAttribute("categoria", prod.getCategoria());
		model.addAttribute("titulo", prod.getTitulo());
		model.addAttribute("descripcion", prod.getDescripcion());
		model.addAttribute("precio", prod.getPrecio());
		model.addAttribute("productid", prod.getId());
		model.addAttribute("id", usuario.getLoggedUser().getId());
		return "subirproducto";
	}
	
	@RequestMapping("/comprarproducto/{num}")
	public String comprarProducto(Model model, @PathVariable Long num) {

		Producto elegido = productoRepo.findById(num).get();
		model.addAttribute("producto", elegido);

		return "comprarproducto";
	}

	@PostMapping("/comprarproducto/inputpedido")
	public String confirmarCompra(Model model, @RequestParam String destino, String productid) {
		Usuario user = usuario.getLoggedUser();

		Producto p = productoRepo.findById(Long.parseLong(productid)).get();
		Pedido pedido = new Pedido(p, destino, user);

		pedidoRepo.save(pedido);
		
		comunicarSenderEmails((int) pedido.getId());

		return "pedidorealizado";
	}
	@GetMapping("/eliminarproducto/{productoid}")
	public String borrarProducto(Model model, @PathVariable Long productoid, HttpServletRequest request) {
		Producto p = productoRepo.findById(productoid).get();
		List<Chat> listaChatsABorrar = chatRepo.findByProducto(p);
		chatRepo.deleteInBatch(listaChatsABorrar);
		Pedido pedidoEncontrado = pedidoRepo.findByProducto(p);
		if(pedidoEncontrado == null) {
			productoRepo.deleteById(productoid);
			model.addAttribute("productoBorrado", p);
		}
		return "productdelete";
	}
	
	
	private void comunicarSenderEmails(int pedidoId) {
		Socket serverSocket = null;
        try {
            serverSocket = new Socket("email", 10001);
            DataOutputStream dos = new DataOutputStream(serverSocket.getOutputStream());
            dos.writeInt(pedidoId);
            dos.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}
