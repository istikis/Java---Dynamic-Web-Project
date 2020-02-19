package com.istikis.masajes.repositorios;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.CallableStatement;
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

import com.istikis.masajes.repositorios.AccesoDatosException;
import com.istikis.masajes.repositorios.ClienteMySQL;
import com.istikis.masajes.repositorios.RepositoriosException;
import com.istikis.masajes.modelo.Cliente;

public class ClienteMySQL implements Dao<Cliente>{
	
	private static final String SQL_GET_ALL = "CALL clientesGetAll()";

	private static final String SQL_GET_BY_ID = "CALL clientesGetById(?)";

	private static final String SQL_INSERT = "CALL clientesInsert(?,?,?,?)";
	
	private static final String SQL_UPDATE = null;

	private static final String SQL_DELETE = null;
	
	private static String url, usuario, password;
	
	private static DataSource pool;
	
	// "SINGLETON"
		private ClienteMySQL(String url, String usuario, String password) {
			this.url = url;
			this.usuario = usuario;
			this.password = password;
		}

		private static ClienteMySQL instancia;

		/**
		 * Se usará para inicializar la instancia
		 * 
		 * @param url
		 * @param usuario
		 * @param password
		 * @return La instancia
		 */
		public static ClienteMySQL getInstancia(String url, String usuario, String password) {
			// Si no existe la instancia...
			if (instancia == null) {
				// ...la creamos
				instancia = new ClienteMySQL(url, usuario, password);
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
		public static ClienteMySQL getInstancia() {
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
		public static ClienteMySQL getInstancia(String entorno) {
			InitialContext initCtx;
			try {
				initCtx = new InitialContext();

				Context envCtx = (Context) initCtx.lookup("java:comp/env");
				DataSource dataSource = (DataSource) envCtx.lookup(entorno);

				ClienteMySQL.pool = dataSource;

				if(instancia == null) {
					instancia = new ClienteMySQL(null, null, null);
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
	public Iterable<Cliente> getAll() {
		try (Connection con = getConexion()) {
			try (CallableStatement s = con.prepareCall(SQL_GET_ALL)) {
				try (ResultSet rs = s.executeQuery()) {
					ArrayList<Cliente> clientes = new ArrayList<>();

					Cliente cliente;

					while (rs.next()) {
						cliente = new Cliente(rs.getInt("idclientes"), rs.getString("nombre"),
								rs.getString("apellidos"), rs.getString("dni"));

						clientes.add(cliente);
					}

					return clientes;
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
	public Cliente getById(Integer id) {
		try (Connection con = getConexion()) {
			try (CallableStatement s = con.prepareCall(SQL_GET_BY_ID)) {
				s.setInt(1, id);
				try (ResultSet rs = s.executeQuery()) {
					Cliente cliente = null;

					if (rs.next()) {
						cliente = new Cliente(rs.getInt("idclientes"), rs.getString("nombre"),
								rs.getString("apellidos"), rs.getString("dni"));
					}

					return cliente;
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
	public Integer insert(Cliente cliente) {
		try (Connection con = getConexion()) {
			try (CallableStatement s = con.prepareCall(SQL_INSERT)) {
				
				s.setString(1, cliente.getNombre());
				s.setString(2, cliente.getApellidos());
				s.setString(3, cliente.getDni());

				s.registerOutParameter(4, java.sql.Types.INTEGER);

				int numeroRegistrosModificados = s.executeUpdate();

				if (numeroRegistrosModificados != 1) {
					throw new RepositoriosException("Número de registros modificados: " + numeroRegistrosModificados);
				}

				return s.getInt(4); // atento a este que era void

			} catch (SQLException e) {
				throw new RepositoriosException("Error al crear la sentencia", e);
			}
		} catch (SQLException e) {
			throw new RepositoriosException("Error al conectar", e);
		}	
	}

	@Override
	public void update(Cliente cliente) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
				
				ps.setString(1, cliente.getNombre());
				ps.setString(2, cliente.getApellidos());
				ps.setString(3, cliente.getDni());
				ps.setLong(4, cliente.getId());
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una update");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al modificar el cliente", e);
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
			throw new AccesoDatosException("Error al borrar el cliente", e);
		}
		
	}

}
