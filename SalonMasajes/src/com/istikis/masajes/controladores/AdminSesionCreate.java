package com.istikis.masajes.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.istikis.masajes.controladores.Globales;
import com.istikis.masajes.modelo.Cliente;
import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Trabajador;
import com.istikis.masajes.modelo.Sesion;

@WebServlet("/admin/add_sesion")
public class AdminSesionCreate extends HttpServlet {
	private static final String SESION_CREATE_JSP = "/WEB-INF/vistas/admin/sesionCreate.jsp";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		
		if(id != null) {
			
			Integer idInteger = new Integer(id);
			Sesion sesion =  Globales.daoSesion.getById(idInteger);
			request.setAttribute("sesion", sesion);
			
			contenidoDesplegables(request);
			
			request.getRequestDispatcher(SESION_CREATE_JSP).forward(request, response);
			
		}
	}
	
	private void contenidoDesplegables(HttpServletRequest request) {
		
		Iterable<Cliente> clientes = Globales.daoCliente.getAll();
		Iterable<Trabajador> trabajadores = Globales.daoTrabajador.getAll();
		Iterable<Servicio> servicios = Globales.daoServicio.getAll();

		request.setAttribute("clientes", clientes);
		request.setAttribute("trabajadores", trabajadores);
		request.setAttribute("servicios", servicios);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String idCliente = request.getParameter("cliente");
		String idTrabajador = request.getParameter("trabajador");
		String idServicio = request.getParameter("servicio");
		String fecha = request.getParameter("fecha");
		String resena = request.getParameter("resena");
		String calificacion = request.getParameter("calificacion");

		Sesion sesion;
		
		try {
			sesion = new Sesion(id, idCliente, idTrabajador, idServicio,
					fecha, resena, calificacion);

			if(!sesion.isCorrecto()) {
				request.setAttribute("sesion", sesion);
				
				session.setAttribute("alertatexto", "Errores de validación en el formulario");
				session.setAttribute("alertanivel", "danger");

				contenidoDesplegables(request);
				
				request.getRequestDispatcher(SESION_CREATE_JSP).forward(request, response);
			}
			
			if (id == null || id.trim().length() == 0) {
				Globales.daoSesion.insert(sesion);;
			} else {
				Globales.daoSesion.update(sesion);;
			}

			response.sendRedirect(request.getContextPath() + "/");

		} catch (Exception e) {
			session.setAttribute("alertatexto", e.getMessage());
			session.setAttribute("alertanivel", "danger");

			e.printStackTrace();

			response.sendRedirect("#");
		}
	}

}
