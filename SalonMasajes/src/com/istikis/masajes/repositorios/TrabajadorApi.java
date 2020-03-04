package com.istikis.masajes.repositorios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.istikis.masajes.modelo.Trabajador;

@WebServlet("/api/trabajadores/*")
public class TrabajadorApi extends HttpServlet {
	private static final String URL_ID_VALIDA = "^/\\d+$";
	private static final long serialVersionUID = 1L;

	private static TreeMap<Integer, Trabajador> trabajadores = new TreeMap<>();

	static {
		trabajadores.put(1, new Trabajador(1, "Trabajador1", "ApeTrabaja1", "79224851T"));
		trabajadores.put(2, new Trabajador(2, "Trabajador2", "ApeTrabaja2", "79224852T"));
	}

	private static Gson gson = new Gson();

	// SELECT
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Integer id = null;

		try {
			id = extraerId(request);

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		if (id == null) {
			out.write(gson.toJson(trabajadores.values()));
			return;
		}

		Trabajador trabajador = trabajadores.get(id);

		if (trabajador == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		} else {
			out.write(gson.toJson(trabajador));
		}
	}

	// INSERT
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			if (extraerId(request) != null) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		String json = extraerJSON(request);

		Trabajador trabajador = gson.fromJson(json, Trabajador.class);

		Integer id = trabajadores.size() == 0 ? 1 : trabajadores.lastKey() + 1;

		trabajador.setId(id);

		trabajadores.put(id, trabajador);

		response.getWriter().write(gson.toJson(trabajador));
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	// UPDATE
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String json = extraerJSON(request);

		Trabajador trabajador = gson.fromJson(json, Trabajador.class);
		
		Integer id = null;
		
		try {
			id = extraerId(request);
			
			if(id == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		if(id != trabajador.getId()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		trabajadores.put(id, trabajador);
		response.getWriter().write(gson.toJson(trabajador));
	}

	// DELETE
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer id = null;
		
		try {
			id = extraerId(request);
			
			if(id == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		trabajadores.remove(id);
		response.getWriter().write("{}");
	}

	private static Integer extraerId(HttpServletRequest request) {
		String path = request.getPathInfo();

		if (path == null || path.equals("/")) {
			return null;
		}

		if (!path.matches(URL_ID_VALIDA)) {
			throw new RuntimeException("URL de petición no válida");
		}
		return Integer.parseInt(path.substring(1));
	}

	private String extraerJSON(HttpServletRequest request) throws IOException {

		BufferedReader br = request.getReader();

		StringBuffer sb = new StringBuffer();

		String linea;

		while ((linea = br.readLine()) != null) {
			sb.append(linea);
		}
		return sb.toString();
	}

}
