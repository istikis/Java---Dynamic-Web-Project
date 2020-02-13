package com.istikis.masajes.controladores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.istikis.masajes.modelo.Trabajador;

@WebServlet("/admin/trabajadores")
public class AdminTrabajadorController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setAttribute("trabajadores", Globales.daoTrabajador.obtenerTodos());
		request.getRequestDispatcher("/WEB-INF/vistas/admin/trabajador.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
