package es.urjc.computadores.pedido;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import es.urjc.computadores.usuario.UserComponent;

@Controller
public class PedidoController implements CommandLineRunner {

	public static final String OUTPUT_DIR = "src/main/resources/static/";

	@Autowired
	private UserComponent usuario;

	@Override
	public void run(String... args) throws Exception {

	}

	@PostConstruct
	public void init() {

	}

	@GetMapping("/gestionenvios")
	public String gestionenvios(Model model) {
		model.addAttribute("vendidos", usuario.getLoggedUser().getPedidosVendidos());
		model.addAttribute("comprados", usuario.getLoggedUser().getPedidosComprados());
		model.addAttribute("userid", usuario.getLoggedUser().getId());

		return "gestionenvios";
	}

	@GetMapping("/generarfactura")
	public String generarFactura(Model model, @RequestParam int id) {
		new Thread(() -> {
			comunicarServicioInternoPDF(id);
		}).start();
		model.addAttribute("vendidos", usuario.getLoggedUser().getPedidosVendidos());
		model.addAttribute("comprados", usuario.getLoggedUser().getPedidosComprados());
		model.addAttribute("userid", usuario.getLoggedUser().getId());
		return "gestionenvios";
	}

	private void comunicarServicioInternoPDF(int pedidoId) {
		Socket serverSocket;
		try {
			serverSocket = new Socket("pdf", 10000);
			DataOutputStream dos = new DataOutputStream(serverSocket.getOutputStream());
			dos.writeInt(pedidoId);

			InputStream is = serverSocket.getInputStream();
			OutputStream os = new FileOutputStream(System.getProperty("user.home") + "/facturas/facturasgeneradas/factura" + pedidoId + ".pdf");

			copy(is, os);

			is.close();
			os.close();
			dos.close();
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] buf = new byte[512];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
			System.out.println(len);
			out.write(buf, 0, len);
		}
	}

}
