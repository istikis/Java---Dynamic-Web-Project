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
import com.istikis.masajes.modelo.Cliente;

@WebServlet("/api/clientes/*")
public class ClienteApi extends HttpServlet {
	private static final String URL_ID_VALIDA = "^/\\d+$";
	private static final long serialVersionUID = 1L;

	private static TreeMap<Integer, Cliente> clientes = new TreeMap<>();

	static {
		clientes.put(1, new Cliente(1, "Rodrigo", "Soto Cid", "79224857T"));
		clientes.put(2, new Cliente(2, "Camila", "Tapia Guajardo", "15887542C"));
		clientes.put(3, new Cliente(3,"Guillermo", "Mexico Lindo", "22554488M"));
	}

	private static Gson gson = new Gson();

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
			out.write(gson.toJson(clientes.values()));
			return;
		}

		Cliente cliente = clientes.get(id);

		if (cliente == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		} else {
			out.write(gson.toJson(cliente));
		}
	}

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

		Cliente cliente = gson.fromJson(json, Cliente.class);

		Integer id = clientes.size() == 0 ? 1 : clientes.lastKey() + 1;

		cliente.setId(id);

		clientes.put(id, cliente);

		response.getWriter().write(gson.toJson(cliente));

		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String json = extraerJSON(request);

		Cliente cliente = gson.fromJson(json, Cliente.class);

		Integer id = null;

		try {
			id = extraerId(request);
			
			if (id == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		if (id != cliente.getId()) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		clientes.put(id, cliente);

		response.getWriter().write(gson.toJson(cliente));
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Integer id = null;

		try {
			id = extraerId(request);

			if (id == null) {
				throw new Exception();
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}

		clientes.remove(id);
		response.getWriter().write("{}");
	}

	private String extraerJSON(HttpServletRequest request) throws IOException {
		BufferedReader br = request.getReader();
		
		StringBuffer sb = new StringBuffer();
		
		String linea;
		
		while((linea = br.readLine()) != null) {
			sb.append(linea);
		}
		return sb.toString();
	}

	private static Integer extraerId(HttpServletRequest request) {
		String path = request.getPathInfo();
		
		if(path == null || path.equals("/")) {
			
			return null;			
		}
		
		if(!path.matches(URL_ID_VALIDA)) {
			throw new RuntimeException("URL de petición no válida");
		}
		return Integer.parseInt(path.substring(1));			
	}

}
