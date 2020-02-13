package com.istikis.masajes.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istikis.masajes.modelo.Trabajador;

@WebServlet("/admin/add_trabajador")
public class AdminTrabajadorCreate extends HttpServlet {
	private static final String TRABAJADOR_CREATE_JSP = "/WEB-INF/vistas/admin/trabajadorCreate.jsp";
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String op = request.getParameter("op");
		
		if (id != null) {
			Trabajador trabajador = Globales.daoTrabajador.obtenerPorId(Long.parseLong(id));
			request.setAttribute("trabajador", trabajador);
		}
		
		request.setAttribute("op", op);
		request.setAttribute("primeravez", true);
		request.getRequestDispatcher(TRABAJADOR_CREATE_JSP).forward(request, response);

	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String op = request.getParameter("op");
		String id = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String dni = request.getParameter("dni");
		
		Trabajador trabajador = null;
		
		switch (op) {
		case "agregar":
			trabajador = new Trabajador(nombre, apellidos, dni);
			Globales.daoTrabajador.agregar(trabajador);
			break;
		case "modificar":
			trabajador = new Trabajador(Long.parseLong(id),nombre, apellidos, dni);
			Globales.daoTrabajador.modificar(trabajador);
			break;

		default:
			throw new RuntimeException("Operación no reconocida");
		}
		response.sendRedirect("trabajadores");
		//request.getRequestDispatcher("/WEB-INF/vistas/admin/trabajador.jsp").forward(request, response);
	}

}
