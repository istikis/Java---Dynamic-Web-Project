package com.istikis.masajes.repositorios;

import java.util.TreeMap;

import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;

import com.istikis.masajes.modelo.Sesion;


@Path("/sesiones")
@Produces("application/json")
@Consumes("application/json")
public class SesionRest {

	private static TreeMap<Integer, Sesion> sesiones = new TreeMap<>();
	
	static {
		sesiones.put(1, new Sesion("1","cliente1", "trabajador", "servicio", "2020-12-12", "reseña", "muy bueno"));
		sesiones.put(2, new Sesion("2","cliente2", "trabajador2", "servicio2", "2020-12-12", "reseña2", "muy bueno2"));
	}
	
	@GET
	public Iterable<Sesion> getAll() {
		return sesiones.values();
	}
	
	@GET
	@Path("/{id}")
	public Sesion getById(@PathParam("id") Integer id) {
		return sesiones.get(id);
	}
	
	@POST
	public Sesion insert(Sesion sesion) {		
		Integer id = sesiones.size() == 0 ? 1 : sesiones.lastKey() +1 ;
		sesion.setId(id);
		
		sesiones.put(id, sesion);
		return sesion;
	}
	
	@PUT
	@Path("/{id}")
	public Sesion update(@PathParam("id") Integer id, Sesion sesion) {
		if(id != sesion.getId()) {
			throw new WebApplicationException("No concuerdan los Ids", Status.BAD_REQUEST);			
		}
		
//		if() {
//			
//		}
		
		return null;
	}
}
