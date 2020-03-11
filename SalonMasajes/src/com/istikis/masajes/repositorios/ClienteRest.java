package com.istikis.masajes.repositorios;

import com.istikis.masajes.controladores.Globales;
import com.istikis.masajes.modelo.Cliente;

import java.util.logging.Logger;

import javax.naming.Context;
import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response.Status;

@Path("/clientes2")
@Produces("application/json")
@Consumes("application/json")
public class ClienteRest {
	//private static final Logger LOGGER = Logger.getLogger(ClienteRest.class.getCanonicalName());

	// @Context
	//private ServletContext context;

	@GET
	public Iterable<Cliente> getAll() {
//		LOGGER.info("Get All!");
//		LOGGER.info(context.toString());
		Iterable<Cliente> clientes = Globales.daoCliente.getAll();
		return clientes;
	}

//	@Operation(summary = "Obtener usuario por id", responses = {
//					@ApiResponse(responseCode = "200", description = "Devuelve el usuario cuyo id es el que se ha pedido", content = {
//					@Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)) }),
//					@ApiResponse(responseCode = "404", description = "No encontrado") })

	@GET
	@Path("/{id}")
	public Cliente getById(@PathParam("id") Integer id) {
		//LOGGER.info("getById(" + id + ")");
		if (id != null) {
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
		//LOGGER.info("update(" + id + ", " + cliente + ")");

		if (id != cliente.getId()) {
			
			//LOGGER.warning("No concuerdan los id: " + id + ", " + cliente);
			
			throw new WebApplicationException("No concuerdan los id", Status.BAD_REQUEST);
		}

		if (cliente.getId() == null) {
			
			//LOGGER.warning("No se ha encontrado el id a modificar: " + id + ", " + cliente);
			
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
