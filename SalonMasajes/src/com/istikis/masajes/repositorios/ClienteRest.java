package com.istikis.masajes.repositorios;

import java.util.TreeMap;

import com.istikis.masajes.modelo.Cliente;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;



@Path("/clientes2")
@Produces("application/json")
@Consumes("application/json")
public class ClienteRest {
	
	private static TreeMap<Integer, Cliente> clientes = new TreeMap<>();
	
	static {
		clientes.put(1, new Cliente(1, "Robertito", "Gigantón", "55412842R"));
		clientes.put(2, new Cliente(2, "Guachupé", "Mono", "12342842R"));
	}
	
	@GET
	public Iterable<Cliente> getAll() {
		return clientes.values();
	}
	
	@GET
	@Path("/{id}")
	public Cliente getById(@PathParam("id") Integer id) {
		return clientes.get(id);
	}
	
	@POST
	public Cliente insert(Cliente cliente) {
		Integer id = clientes.size() == 0 ? 1 : clientes.lastKey() + 1;
		cliente.setId(id);
		
		clientes.put(id, cliente);
		return cliente;
	}
	
	@PUT
	@Path("/{id}")
	public Cliente update(@PathParam("id") Integer id, Cliente cliente) {
		if(id != cliente.getId()) {
			throw new WebApplicationException("No concuerdan los Ids", Status.BAD_REQUEST);
		}
		
		if(!clientes.containsKey(id)) {
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}
		
		clientes.put(id, cliente);
		return cliente;
	}
	
	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		clientes.remove(id);
		return "{}";
	}
	
}
