package com.istikis.masajes.repositorios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.istikis.masajes.repositorios.AccesoDatosException;
import com.istikis.masajes.modelo.Servicio;

public class ServicioMySQL implements Dao<Servicio> {
	
	private static final String SQL_SELECT = "SELECT * FROM servicios";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM servicios WHERE idservicios=?";

	private static final String SQL_INSERT = "INSERT INTO servicios (nombre, precio) VALUES (?,?)";
	private static final String SQL_UPDATE = "UPDATE servicios SET nombre=?, precio=? WHERE idservicios=?";
	private static final String SQL_DELETE = "DELETE FROM servicios WHERE idservicios=?";
	
	private static String url, usuario, password;
	
	// SINGLETON
			private static ServicioMySQL instancia;
			
			private ServicioMySQL(String url, String usuario, String password) {
				ServicioMySQL.url = url;
				ServicioMySQL.usuario = usuario;
				ServicioMySQL.password = password;
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					throw new AccesoDatosException("No se ha encontrado el driver de MySQL");
				}
			}
			
			public static ServicioMySQL getInstancia(String pathConfiguracion) {
				try {
					if (instancia == null) {
						Properties configuracion = new Properties();
						configuracion.load(new FileInputStream(pathConfiguracion));

						instancia = new ServicioMySQL(configuracion.getProperty("mysql.url"),
								configuracion.getProperty("mysql.usuario"), configuracion.getProperty("mysql.password"));
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
					throw new AccesoDatosException("Error en la conexión a la base de datos");
				}
			}
			

	@Override
	public Iterable<Servicio> obtenerTodos() {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
				try(ResultSet rs = ps.executeQuery()){
					
					ArrayList<Servicio> servicios = new ArrayList<>();
					
					while(rs.next()) {
						servicios.add(new Servicio(
								rs.getLong("idservicios"), 
								rs.getString("nombre"), 
								rs.getBigDecimal("precio")
								));
					}
					
					return servicios;
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener todos los servicios", e);
		}
	}

	@Override
	public Servicio obtenerPorId(Long id) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
				
				ps.setLong(1, id);
				
				try(ResultSet rs = ps.executeQuery()){
									
					if(rs.next()) {
						return new Servicio(
								rs.getLong("idservicios"), 
								rs.getString("nombre"), 
								rs.getBigDecimal("precio")
								);
					} else {
						return null;
					}
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener el Servicio id: " + id, e);
		}
	}

	@Override
	public void agregar(Servicio servicio) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
				
				ps.setString(1, servicio.getNombre());
				ps.setBigDecimal(2, servicio.getPrecio());
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una insert");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al insertar el servicio", e);
		}
	}

	@Override
	public void modificar(Servicio servicio) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
				
				ps.setString(1, servicio.getNombre());
				ps.setBigDecimal(2, servicio.getPrecio());
				ps.setLong(3, servicio.getId());
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una update");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al modificar el servicio", e);
		}
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
			throw new AccesoDatosException("Error al borrar el servicio", e);
		}
	}
	
}


