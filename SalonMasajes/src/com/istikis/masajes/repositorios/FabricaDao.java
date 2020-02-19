package com.istikis.masajes.repositorios;

import com.istikis.masajes.modelo.Cliente;
import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Sesion;
import com.istikis.masajes.modelo.Trabajador;

public interface FabricaDao {
	
	Dao<Sesion> getSesionDao();
	Dao<Cliente> getClienteDao();
	Dao<Trabajador> getTrabajadorDao();
	Dao<Servicio> getServicioDao();
	
//	private String pathConfiguracion;
//	private String tipo;
//	
//	//SINGLETON
//	private static FabricaDao instancia;
//	
//
//	public static FabricaDao getInstancia(String pathConfiguracion) {
//		if(instancia == null) {
//			instancia = new FabricaDao(pathConfiguracion);
//		}
//		return instancia;
//	}
//
//	private FabricaDao(String pathConfiguracion) {
//		
//		this.pathConfiguracion = pathConfiguracion;
//		
//		Properties configuracion = new Properties();
//		
//		try {
//			configuracion.load(new FileInputStream(pathConfiguracion));
//			tipo = configuracion.getProperty("tipo");
//		}catch (IOException e) {
//			throw new AccesoDatosException("No se ha podido leer el fichero de configuración");
//		}
//	}
//	//FIN SINGLETON
//	
//	public Dao<Sesion> getInstanciaSesion() {
//		if("mysql".equals(tipo)) {
//			
//			return SesionMySQL.getInstancia(pathConfiguracion);
//		   //return SesionMySQL.getInstancia(pathConfiguracion);
//		
//		}else {
//			throw new AccesoDatosException("No se reconoce el tipo " + tipo);
//		}
//		
//	}
//	
//	public Dao<Cliente> getInstanciaCliente() {
//		if("mysql".equals(tipo)) {
//			
//		   return ClienteMySQL.getInstancia(pathConfiguracion);
//		
//		}else {
//			throw new AccesoDatosException("No se reconoce el tipo " + tipo);
//		}
//		
//	}
//	
//	public Dao<Trabajador> getInstanciaTrabajador() {
//		if("mysql".equals(tipo)) {
//			
//		   return TrabajadorMySQL.getInstancia(pathConfiguracion);
//		
//		}else {
//			throw new AccesoDatosException("No se reconoce el tipo " + tipo);
//		}
//		
//	}
//	
//	public Dao<Servicio> getInstanciaServicio() {
//		if("mysql".equals(tipo)) {
//			
//		   return ServicioMySQL.getInstancia(pathConfiguracion);
//		
//		}else {
//			throw new AccesoDatosException("No se reconoce el tipo " + tipo);
//		}
//		
//	}
}
