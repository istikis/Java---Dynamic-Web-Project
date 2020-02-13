package com.istikis.masajes.controladores;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Trabajador;


@WebServlet("/admin/add_servicio")
public class AdminServicioCreate extends HttpServlet {
	private static final String SERVICIO_CREATE_JSP = "/WEB-INF/vistas/admin/servicioCreate.jsp";
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String op = request.getParameter("op");
		
		if (id != null) {
			Servicio servicio = Globales.daoServicio.obtenerPorId(Long.parseLong(id));
			request.setAttribute("servicio", servicio);
		}
		
		request.setAttribute("op", op);
		request.setAttribute("primeravez", true);
		request.getRequestDispatcher(SERVICIO_CREATE_JSP).forward(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String op = request.getParameter("op");
		String id = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String precioString = request.getParameter("precio");
		
		BigDecimal precio= new BigDecimal(precioString);
		
		Servicio servicio = null;
		
		switch (op) {
		case "agregar":
			servicio = new Servicio(nombre, precio);
			Globales.daoServicio.agregar(servicio);
			break;
		case "modificar":
			servicio = new Servicio(Long.parseLong(id), nombre, precio);
			Globales.daoServicio.modificar(servicio);
			break;

		default:
			throw new RuntimeException("Operación no reconocida");
		}
		response.sendRedirect("servicios");
	}

}
