package com.istikis.masajes.controladores;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istikis.masajes.modelo.Cliente;
import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Sesion;
import com.istikis.masajes.modelo.Trabajador;

@WebServlet("/admin/sesion")
public class AdminSesionController extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String id = request.getParameter("id");
		
		if(id != null) {
			Integer idInteger = new Integer(id);
			
			Sesion sesion = Globales.daoSesion.obtenerPorId(idInteger);
			
			request.setAttribute("sesion", sesion);
		}
		
		Iterable<Cliente> clientes = Globales.daoCliente.obtenerTodos();
		Iterable<Trabajador> trabajadores = Globales.daoTrabajador.obtenerTodos();
		Iterable<Servicio> servicios = Globales.daoServicio.obtenerTodos();
		
		request.setAttribute("clientes", clientes);
		request.setAttribute("trabajadores", trabajadores);
		request.setAttribute("servicios", servicios);
		
		request.getRequestDispatcher("/WEB-INF/vistas/admin/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//típico en MVC - recoger los datos
		//Importante poner bien todos los "name" para poder recoger los datos en sesion.jsp en el ejemplo del profe
		
		String id = request.getParameter("id");
		String idCliente = request.getParameter("cliente");
		String idTrabajador = request.getParameter("trabajador");
		String idServicio = request.getParameter("servicio");
		String fecha = request.getParameter("fecha");
		String resena = request.getParameter("resena");
		String calificacion = request.getParameter("calificacion");
		
		//ahora necesito un objetos de tipo sesion
		//para guardar una fila en la base de datos
		
		Cliente cliente;
		Trabajador trabajador;
		Servicio servicio;
		Sesion sesion;
		
		//copia pega de sesionmysql
		//partes
		
//		cliente = new Cliente(Integer.parseInt(idCliente), null, null, null );
//		trabajador = new Trabajador(Integer.parseInt(idTrabajador), null, null, null);
//		servicio = new Servicio(Integer.parseInt(idServicio), null, null);
				
		//globales isertan nuevo o modificar el anterio si teniamos id
		// e insertamos el objeto de sesion
		// 
		
		try {
			// Partes
			cliente = new Cliente(Integer.parseInt(idCliente), null, null, null);

			trabajador = new Trabajador(Integer.parseInt(idTrabajador), null, null, null);

			servicio = new Servicio(Integer.parseInt(idServicio), null, null);

			// Completo

			sesion = new Sesion(null, cliente, trabajador, servicio,
					new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fecha), resena, calificacion);
			
			if (id == null || id.trim().length() == 0) {
				Globales.daoSesion.agregar(sesion);;
			} else {
				sesion.setId(Integer.parseInt(id));
				Globales.daoSesion.modificar(sesion);
			}

		} catch (NumberFormatException | ParseException e) {
			throw new ServletException("Formato de ids o fecha incorrectos", e);
		}
		
		response.sendRedirect(request.getContextPath() + "/");
	}

}
