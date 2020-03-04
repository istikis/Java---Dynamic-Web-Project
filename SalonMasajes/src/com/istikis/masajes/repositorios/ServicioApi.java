package com.istikis.masajes.repositorios;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.istikis.masajes.modelo.Servicio;

@WebServlet("/api/servicios/*")
public class ServicioApi extends HttpServlet {
	private static final String URL_ID_VALIDA = "^/\\d+$";
	private static final long serialVersionUID = 1L;
	
	private static TreeMap<Integer, Servicio> servicios = new TreeMap<>();
	
	static {
		servicios.put(1, new Servicio(1, "Lavado de cara", new BigDecimal(50)));
		servicios.put(2, new Servicio(2, "Lavado de Poto", new BigDecimal(80.50)));
	}
	
	private static Gson gson = new Gson();
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		PrintWriter out = response.getWriter();
		Integer id = null;
		
		try {
			id = extraerId(request);
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
//		if(id) {
//			
//		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPut(request, response);
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doDelete(request, response);
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
