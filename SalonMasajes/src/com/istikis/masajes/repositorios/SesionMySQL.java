package com.istikis.masajes.repositorios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

import com.istikis.masajes.repositorios.RepositoriosException;
import com.istikis.masajes.modelo.Sesion;
import com.istikis.masajes.modelo.Cliente;
import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Trabajador;

public class SesionMySQL implements Dao<Sesion> {
	
	private static final String SQL_GET_ALL = "SELECT * \r\n" + "FROM sesiones ses\r\n"
			+ "JOIN clientes c ON c.idclientes = ses.clientes_idclientes\r\n"
			+ "JOIN trabajadores t ON t.idtrabajadores = ses.trabajadores_idtrabajadores\r\n"
			+ "JOIN servicios s ON s.idservicios = ses.servicios_idservicios;";
	
	private static final String SQL_GET_ID = "SELECT * FROM sesiones WHERE id=?";
	
	private static String url, usuario, password;
	
	// SINGLETON
			private static SesionMySQL instancia;
			
			private SesionMySQL(String url, String usuario, String password) {
				SesionMySQL.url = url;
				SesionMySQL.usuario = usuario;
				SesionMySQL.password = password;
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					throw new AccesoDatosException("No se ha encontrado el driver de MySQL");
				}
			}
			
			public static SesionMySQL getInstancia(String pathConfiguracion) {
				try {
					if (instancia == null) {
						Properties configuracion = new Properties();
						configuracion.load(new FileInputStream(pathConfiguracion));

						instancia = new SesionMySQL(configuracion.getProperty("mysql.url"),
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
				new com.mysql.cj.jdbc.Driver();
				return DriverManager.getConnection(url, usuario, password);
			} catch (SQLException e) {
				throw new RepositoriosException(
						"No se ha podido conectar a la base de datos " + url + ":" + usuario + ":" + password, e);
			}
		}

	@Override
	public Iterable<Sesion> getAll() {
		try (Connection con = getConexion()) {
			try (Statement s = con.createStatement()) {
				try (ResultSet rs = s.executeQuery(SQL_GET_ALL)) {
					ArrayList<Sesion> sesiones = new ArrayList<>();

					Cliente cliente;
					Trabajador trabajador;
					Servicio servicio;
					Sesion sesion;

					while (rs.next()) {
						//Partes
						cliente = new Cliente(
								rs.getInt("clientes_idclientes"), 
								rs.getString("c.nombre"),
								rs.getString("c.apellidos"), 
								rs.getString("c.dni")
								);

						trabajador = new Trabajador(
								rs.getInt("trabajadores_idtrabajadores"), 
								rs.getString("t.nombre"),
								rs.getString("t.apellidos"), 
								rs.getString("t.dni")
								);

						servicio = new Servicio(
								rs.getInt("servicios_idservicios"), 
								rs.getString("s.nombre"),
								rs.getBigDecimal("s.precio")
								);

						//Completo
						sesion = new Sesion(
								rs.getInt("idservicios"), 
								cliente, 
								trabajador, 
								servicio,
								rs.getTimestamp("fecha"), 
								rs.getString("resena"), 
								rs.getString("calificacion"));
						
						//Agregar
						sesiones.add(sesion);
					}
					
					return sesiones;
				} catch (SQLException e) {
					throw new RepositoriosException("Error al acceder a los registros", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al crear la sentencia", e);
			}
		} catch (SQLException e) {
			throw new RepositoriosException("Error al conectar", e);
		}
	}

	@Override
	public Sesion getById(Integer id) {
		try (Connection con = getConexion()) {
			try (PreparedStatement ps = con.prepareStatement(SQL_GET_ID)) {
				ps.setInt(1, id);
				
				try (ResultSet rs = ps.executeQuery()) {
					Cliente cliente;
					Trabajador trabajador;
					Servicio servicio;
					
					Sesion sesion = null;

					if (rs.next()) {
						//Partes
						cliente = new Cliente(
								rs.getInt("clientes_idclientes"), 
								null, 
								null, 
								null
								);

						trabajador = new Trabajador(
								rs.getInt("trabajadores_idtrabajadores"), 
								null, 
								null, 
								null
								);

						servicio = new Servicio(
								rs.getInt("servicios_idservicios"), 
								null, 
								null
								);

						//Completo
						sesion = new Sesion(
								rs.getInt("id"), 
								cliente, 
								trabajador, 
								servicio,
								rs.getTimestamp("fecha"), 
								rs.getString("resena"), 
								rs.getString("calificacion")
								);
					}
					
					return sesion;
				} catch (SQLException e) {
					throw new RepositoriosException("Error al acceder a los registros", e);
				}
			} catch (SQLException e) {
				throw new RepositoriosException("Error al crear la sentencia", e);
			}
		} catch (SQLException e) {
			throw new RepositoriosException("Error al conectar", e);
		}
	}

	@Override
	public Integer insert(Sesion sesion) {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		
	}

	@Override
	public void update(Sesion sesion) {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		
	}

	@Override
	public void delete(Integer id) {
		throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		
	}

}
