package com.istikis.masajes.repositorios;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.TreeMap;

import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;

import com.istikis.masajes.modelo.Cliente;
import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Sesion;
import com.istikis.masajes.modelo.Trabajador;


@Path("/sesiones")
@Produces("application/json")
@Consumes("application/json")
public class SesionRest {

	private static TreeMap<Integer, Sesion> sesiones = new TreeMap<>();
	
	static {
		
		Cliente cliente;
		Trabajador trabajador;
		Servicio servicio;
		
		sesiones.put(1,new Sesion(
				1, 
				cliente = new Cliente(1, "Juan", "Clientero", "79224571C"), 
				trabajador = new Trabajador(1, "Pedro", "Currante", "79224571T"), 
				servicio = new Servicio(1, "Exfoliación con cactus", new BigDecimal(50.50)), 
				new GregorianCalendar(1982, 2-1, 17, 17, 00, 00).getTime(), 
				"El mejor servicio que he recibido en mi vida. Oh My God! jajaja", 
				"Para repetir"
				));
		 
		sesiones.put(2,new Sesion(
				2, 
				cliente = new Cliente(2, "Juana", "Clientera", "79224571C"), 
				trabajador = new Trabajador(2, "Lupita", "Currante", "79224571T"), 
				servicio = new Servicio(2, "Masaje", new BigDecimal(60.75)), 
				new GregorianCalendar(2020, 2-1, 28).getTime(), 
				"No puede ser que se den servicios tan malos. Mierda de masaje", 
				"No recomendable"
				));
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
		
		if(!sesiones.containsKey(id)) {
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}
		
		sesiones.put(id, sesion);
		return sesion;
	}
	
	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		sesiones.remove(id);
		return "{}";
	}
}
