package com.istikis.masajes.repositorios;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

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
	
	private static final String SQL_DELETE = "CALL sesionesDelete(?)";
	
	private static String url, usuario, password;
	private static DataSource pool;
	
	// "SINGLETON"
			private SesionMySQL(String url, String usuario, String password) {
				this.url = url;
				this.usuario = usuario;
				this.password = password;
			}

			private static SesionMySQL instancia;

			/**
			 * Se usará para inicializar la instancia
			 * 
			 * @param url
			 * @param usuario
			 * @param password
			 * @return La instancia
			 */
			public static SesionMySQL getInstancia(String url, String usuario, String password) {
				// Si no existe la instancia...
				if (instancia == null) {
					// ...la creamos
					instancia = new SesionMySQL(url, usuario, password);
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
			public static SesionMySQL getInstancia() {
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
			public static SesionMySQL getInstancia(String entorno) {
				InitialContext initCtx;
				try {
					initCtx = new InitialContext();

					Context envCtx = (Context) initCtx.lookup("java:comp/env");
					DataSource dataSource = (DataSource) envCtx.lookup(entorno);

					SesionMySQL.pool = dataSource;

					if(instancia == null) {
						instancia = new SesionMySQL(null, null, null);
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
		try (Connection con = getConexion()) {
			try(CallableStatement s = con.prepareCall(SQL_DELETE)) {
				
				s.setLong(1, id);
				
				int numeroRegistrosModificados = s.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una delete");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al borrar el cliente", e);
		}
		//throw new UnsupportedOperationException("NO ESTA IMPLEMENTADO");
		
	}

}
