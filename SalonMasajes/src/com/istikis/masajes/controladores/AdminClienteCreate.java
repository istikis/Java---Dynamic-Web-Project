package com.istikis.masajes.controladores;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istikis.masajes.modelo.Cliente;


@WebServlet("/admin/add_cliente")
public class AdminClienteCreate extends HttpServlet {
	private static final String CLIENTE_CREATE_JSP = "/WEB-INF/vistas/admin/clienteCreate.jsp";
	private static final long serialVersionUID = 1L;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String op = request.getParameter("op");
		
		if (id != null) {
			Cliente cliente = Globales.daoCliente.obtenerPorId(Long.parseLong(id));
			request.setAttribute("cliente", cliente);
		}
		
		request.setAttribute("op", op);
		request.setAttribute("primeravez", true);
		request.getRequestDispatcher(CLIENTE_CREATE_JSP).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		String op = request.getParameter("op");
		String id = request.getParameter("id");
		String nombre = request.getParameter("nombre");
		String apellidos = request.getParameter("apellidos");
		String dni = request.getParameter("dni");
		
		Cliente cliente = null;
		
		switch (op) {
		case "agregar":
			cliente = new Cliente(nombre, apellidos, dni);
			Globales.daoCliente.agregar(cliente);
			break;
		case "modificar":
			cliente = new Cliente(Long.parseLong(id), nombre, apellidos, dni);
			Globales.daoCliente.modificar(cliente);
			break;

		default:
			throw new RuntimeException("Operación no reconocida");
		}
		response.sendRedirect("clientes");
	}

}
