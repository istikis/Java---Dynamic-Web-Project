package com.istikis.masajes.repositorios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.istikis.masajes.repositorios.AccesoDatosException;
import com.istikis.masajes.controladores.Globales;
import com.istikis.masajes.modelo.Actuaciones;

public class ActuacionesMySQL implements Dao<Actuaciones>{
	
	private static final String SQL_SELECT = "SELECT * FROM actuaciones";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM actuaciones WHERE id=?";

	private static final String SQL_INSERT = "INSERT INTO actuaciones (idCliente, idTrabajador, idServicio, fecha, resena, calificacion) VALUES (?,?,?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE actuaciones SET nombre=?, url=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM actuaciones WHERE id=?";
	
	
	
	private static String url, usuario, password;
	
	// SINGLETON
	private static ActuacionesMySQL instancia;
	
	private ActuacionesMySQL(String url, String usuario, String password) {
		ActuacionesMySQL.url = url;
		ActuacionesMySQL.usuario = usuario;
		ActuacionesMySQL.password = password;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new AccesoDatosException("No se ha encontrado el driver de MySQL");
		}
		
	}
	
	public static ActuacionesMySQL getInstancia(String pathConfiguracion) {
		try {
			if(instancia == null) {
				Properties configuracion = new Properties();
				configuracion.load(new FileInputStream(pathConfiguracion));
				
				instancia = new ActuacionesMySQL(						
						configuracion.getProperty("mysql.url"),
						configuracion.getProperty("mysql.usuario"),
						configuracion.getProperty("mysql.password") );				
			}
			return instancia;
			
		} catch (FileNotFoundException e) {
			throw new AccesoDatosException("Fichero de configuración no encontrado", e);
		} catch (IOException e) {
			throw new AccesoDatosException("Fallo de lectura/escritura al fichero", e);
		}
	}
	
	// FIN SINGLETON
	
	private Connection getConexion() {
		try {
			return DriverManager.getConnection(url, usuario, password);
		} catch (SQLException e) {
			throw new AccesoDatosException("Error en la conexión a la Base de Datos");
		}
	}
	
	@Override
	public Iterable<Actuaciones> obtenerTodos() {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
				try(ResultSet rs = ps.executeQuery()){
					
					ArrayList<Actuaciones> actuaciones = new ArrayList<>();
					
					while(rs.next()) {
						//actuaciones.add(new Actuaciones(Actuaciones cliente = new Actuaciones(););
					}
					
					return actuaciones;
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener todas las actuaciones", e);
		}
	}

@Override
public Actuaciones obtenerPorId(Long id) {
//		try (Connection con = getConexion()) {
//			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
//				ps.setLong(1, id);
//				
//				try(ResultSet rs = ps.executeQuery()){
//									
//					if(rs.next()) {
//						return new Actuaciones(
//								rs.getObject("clientes_idclientes"), 
//								rs.getObject("trabajadores_idtrabajadores"),
//								rs.getObject("servicios_idServicios"),
//								rs.getDate("fecha"),
//								rs.getString("resena"),
//								rs.getString("calificacion")
//								);
//					} else {
						return null;
//					}
//				}
//			}
//		} catch (SQLException e) {
//			throw new AccesoDatosException("Error al obtener las actuaciones id: " + id, e);
//		}
}

	@Override
	public void agregar(Actuaciones actuaciones) {
//		try (Connection con = getConexion()) {
//			try(PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
//				ps.setObject(1, actuaciones.getIdCliente());
//				ps.setObject(2, actuaciones.getIdTrabajador());
//				ps.setObject(3, actuaciones.getIdServicio());
//				ps.setDate(4, actuaciones.getFecha());
//				ps.setString(5, actuaciones.getResena());
//				ps.setString(6, actuaciones.getCalificacion());
//				
//				
//				int numeroRegistrosModificados = ps.executeUpdate();
//				
//				if(numeroRegistrosModificados != 1) {
//					throw new AccesoDatosException("Se ha hecho más o menos de una insert");
//				}
//			}
//		} catch (SQLException e) {
//			ResultSet.get
//			throw new AccesoDatosException("Error al insertar Actuaciones", e);
//		}
		
	}

	@Override
	public void modificar(Actuaciones objeto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrar(Long id) {
		
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
				
				ps.setLong(1, id);
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una delete");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al borrar el sesión", e);
		}
	}

	
	
	
}
