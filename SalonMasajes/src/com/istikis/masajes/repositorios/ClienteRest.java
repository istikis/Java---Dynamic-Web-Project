package com.istikis.masajes.repositorios;

import com.istikis.masajes.controladores.Globales;
import com.istikis.masajes.modelo.Cliente;

import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;


@Path("/clientes2")
@Produces("application/json")
@Consumes("application/json")
public class ClienteRest {


	@GET
	public Iterable<Cliente> getAll() {
		Iterable<Cliente> clientes = Globales.daoCliente.getAll();
		return clientes;
	}

	@GET
	@Path("/{id}")
	public Cliente getById(@PathParam("id") Integer id) {
		if(id != null) {
			Cliente cliente = Globales.daoCliente.getById(id);
			return cliente;
		}
		return null;
	}

	@POST
	public Cliente insert(Cliente cliente) {
		Globales.daoCliente.insert(cliente);
		return cliente;
	}

	@PUT
	@Path("/{id}")
	public Cliente update(@PathParam("id") Integer id, Cliente cliente) {

		if(id != cliente.getId()) {
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}
		
		if(cliente.getId() == null) {
			throw new WebApplicationException("No se ha encontrado el id a modificar", Status.NOT_FOUND);
		}
		
		Globales.daoCliente.update(cliente);
		return cliente;
	}

	@DELETE
	@Path("/{id}")
	public String delete(@PathParam("id") Integer id) {
		Globales.daoCliente.delete(id);
		return "{}";
	}
}
