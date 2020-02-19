package com.istikis.masajes.repositorios;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import com.istikis.masajes.repositorios.RepositoriosException;
import com.istikis.masajes.modelo.Cliente;
import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Sesion;
import com.istikis.masajes.modelo.Trabajador;

public class FabricaDaoProperties implements FabricaDao{
	
	private String tipo;
	private String url, usuario, password;
	
	//SINGLETON
		private static FabricaDaoProperties instancia;
		
		/**
		 * Recoger instancia existente
		 * @return (puede valer null)
		 */
		public static FabricaDaoProperties getInstancia() {
			return instancia;
		}
		
		/**
		 * Crear una nueva instancia con un path
		 * @param pathProperties
		 * @return
		 */
		public static FabricaDaoProperties getInstancia(String pathProperties) {
			if(instancia == null) {
				instancia = new FabricaDaoProperties(pathProperties);
			}
			
			return instancia;
		}
		
		private FabricaDaoProperties(String pathProperties) {
			Properties props = new Properties();

			try {
				props.load(new FileReader(pathProperties));

				tipo = props.getProperty("tipo");
				url = props.getProperty(tipo + ".url");
				usuario = props.getProperty(tipo + ".usuario");
				password = props.getProperty(tipo + ".password");
			} catch (IOException e) {
				throw new RepositoriosException("No se ha podido leer el fichero de properties: " + pathProperties, e);
			}
		}

		@Override
		public Dao<Sesion> getSesionDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			
			// HAY QUE MODIFICAR EL SINGLETON QUE CORRESPONDE A SESION
//			switch (tipo) {
//			case "mysql": return SesionMySQL.getInstancia(pathConfiguracion);
//			default:
//				throw new RepositoriosException("No reconozco el tipo " + tipo);
//			}
			
			return null;//ELIMINAR ESTE RETURN!!
		}

		@Override
		public Dao<Cliente> getClienteDao() {
			if(tipo == null) {
				throw new RepositoriosException("No se ha recibido ningún tipo");
			}
			
			switch (tipo) {
			case "mysql": return ClienteMySQL.getInstancia(url, usuario, password);
			default:
				throw new RepositoriosException("No reconozco el tipo " + tipo);
			}
		}

		@Override
		public Dao<Trabajador> getTrabajadorDao() {
			
			// LO MISMO QUE EL DE ARRIBA, PROBLEMAS CON EL SINGLETON
//			if(tipo == null) {
//				throw new RepositoriosException("No se ha recibido ningún tipo");
//			}
//			
//			switch (tipo) {
//			case "mysql": return TrabajadorMySQL.getInstancia(url, usuario, password);
//			default:
//				throw new RepositoriosException("No reconozco el tipo " + tipo);
//			}
			return null;
		}

		@Override
		public Dao<Servicio> getServicioDao() {
			
			//PROBLEMAS CON EL SINGLETON
//			if(tipo == null) {
//				throw new RepositoriosException("No se ha recibido ningún tipo");
//			}
//			
//			switch (tipo) {
//			case "mysql": return ServicioMySQL.getInstancia(url, usuario, password);
//			default:
//				throw new RepositoriosException("No reconozco el tipo " + tipo);
//			}
			
			return null;
		}

	

}
