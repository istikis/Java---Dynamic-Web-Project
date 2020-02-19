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

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.istikis.masajes.modelo.Servicio;

public class ServicioMySQL implements Dao<Servicio> {
	
	private static final String SQL_SELECT = "SELECT * FROM servicios";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM servicios WHERE idservicios=?";

	private static final String SQL_INSERT = "INSERT INTO servicios (nombre, precio) VALUES (?,?)";
	private static final String SQL_UPDATE = "UPDATE servicios SET nombre=?, precio=? WHERE idservicios=?";
	private static final String SQL_DELETE = "DELETE FROM servicios WHERE idservicios=?";
	
	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
			private ServicioMySQL(String url, String usuario, String password) {
				this.url = url;
				this.usuario = usuario;
				this.password = password;
			}

			private static ServicioMySQL instancia;

			/**
			 * Se usará para inicializar la instancia
			 * 
			 * @param url
			 * @param usuario
			 * @param password
			 * @return La instancia
			 */
			public static ServicioMySQL getInstancia(String url, String usuario, String password) {
				// Si no existe la instancia...
				if (instancia == null) {
					// ...la creamos
					instancia = new ServicioMySQL(url, usuario, password);
					// Si existe la instancia, pero sus valores no concuerdan...
				} else if (!instancia.url.equals(url) || !instancia.usuario.equals(usuario)
						|| !instancia.password.contentEquals(password)) {
					// ...lanzar un error
					throw new RepositoriosException("No se pueden cambiar los valores de la instancia una vez inicializada");
				}

				// Devolver la instancia recién creada o la existente (cuyos datos coinciden con
				// los que tiene)
				return instancia;
			}

			/**
			 * Se usará para recuperar la instancia ya existente
			 * 
			 * @return devuelve la instancia ya existente
			 */
			public static ServicioMySQL getInstancia() {
				// Si no existe la instancia...
				if (instancia == null) {
					// ...no se puede obtener porque no sabemos los datos de URL, usuario y password
					throw new RepositoriosException("Necesito que me pases URL, usuario y password");
				}

				// Si ya existe, se devuelve
				return instancia;
			}

			/**
			 * Usaremos un pool de conexiones determinado
			 * 
			 * @return devuelve la instancia del pool de conexiones
			 */
			public static ServicioMySQL getInstancia(String entorno) {
				InitialContext initCtx;
				try {
					initCtx = new InitialContext();

					Context envCtx = (Context) initCtx.lookup("java:comp/env");
					DataSource dataSource = (DataSource) envCtx.lookup(entorno);

					ServicioMySQL.pool = dataSource;

					if(instancia == null) {
						instancia = new ServicioMySQL(null, null, null);
					}
					
					return instancia;
				} catch (NamingException e) {
					throw new RepositoriosException("No se ha podido conectar al Pool de conexiones " + entorno);
				}
			}
			// FIN "SINGLETON"
			
			private Connection getConexion() {
				try {
					if (pool == null) {
						new com.mysql.cj.jdbc.Driver();
						return DriverManager.getConnection(url, usuario, password);
					} else {
						return pool.getConnection();
					}
				} catch (SQLException e) {
					System.err
							.println("IPARTEK: Error de conexión a la base de datos: " + url + ":" + usuario + ":" + password);
					e.printStackTrace();

					throw new RepositoriosException("No se ha podido conectar a la base de datos", e);
				}
			}
	@Override
	public Iterable<Servicio> getAll() {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
				try(ResultSet rs = ps.executeQuery()){
					
					ArrayList<Servicio> servicios = new ArrayList<>();
					
					while(rs.next()) {
						servicios.add(new Servicio(
								rs.getInt("idservicios"), 
								rs.getString("nombre"), 
								rs.getBigDecimal("precio")));
					}
					
					return servicios;
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener todos los servicios", e);
		}
	}

	@Override
	public Servicio getById(Integer id) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
				
				ps.setLong(1, id);
				
				try(ResultSet rs = ps.executeQuery()){
									
					if(rs.next()) {
						return new Servicio(
								rs.getInt("idservicios"), 
								rs.getString("nombre"), 
								rs.getBigDecimal("precio"));
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
	public Integer insert(Servicio servicio) {
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
		return null;// return al cambiar de void a integer
	}

	@Override
	public void update(Servicio servicio) {
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
	public void delete(Integer id) {
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


