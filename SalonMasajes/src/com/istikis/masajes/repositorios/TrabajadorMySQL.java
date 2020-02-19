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

import com.istikis.masajes.repositorios.AccesoDatosException;
import com.istikis.masajes.modelo.Trabajador;


public class TrabajadorMySQL implements Dao<Trabajador> {

	private static final String SQL_SELECT = "SELECT * FROM trabajadores";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM trabajadores WHERE idtrabajadores=?";

	private static final String SQL_INSERT = "INSERT INTO trabajadores (nombre, apellidos, dni) VALUES (?,?,?)";
	private static final String SQL_UPDATE = "UPDATE trabajadores SET nombre=?, apellidos=?, dni=? WHERE idtrabajadores=?";
	private static final String SQL_DELETE = "DELETE FROM trabajadores WHERE idtrabajadores=?";
	
	private static String url, usuario, password;
	
	private static DataSource pool;
	
	// "SINGLETON"
			private TrabajadorMySQL(String url, String usuario, String password) {
				this.url = url;
				this.usuario = usuario;
				this.password = password;
			}

			private static TrabajadorMySQL instancia;

			/**
			 * Se usará para inicializar la instancia
			 * 
			 * @param url
			 * @param usuario
			 * @param password
			 * @return La instancia
			 */
			public static TrabajadorMySQL getInstancia(String url, String usuario, String password) {
				// Si no existe la instancia...
				if (instancia == null) {
					// ...la creamos
					instancia = new TrabajadorMySQL(url, usuario, password);
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
			public static TrabajadorMySQL getInstancia() {
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
			public static TrabajadorMySQL getInstancia(String entorno) {
				InitialContext initCtx;
				try {
					initCtx = new InitialContext();

					Context envCtx = (Context) initCtx.lookup("java:comp/env");
					DataSource dataSource = (DataSource) envCtx.lookup(entorno);

					TrabajadorMySQL.pool = dataSource;

					if(instancia == null) {
						instancia = new TrabajadorMySQL(null, null, null);
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
	public Iterable<Trabajador> getAll() {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
				try(ResultSet rs = ps.executeQuery()){
					
					ArrayList<Trabajador> trabajadores = new ArrayList<>();
					
					while(rs.next()) {
						trabajadores.add(new Trabajador(
								rs.getInt("idtrabajadores"), 
								rs.getString("nombre"),
								rs.getString("apellidos"), 
								rs.getString("dni")));
					}
					
					return trabajadores;
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener todos los clientes", e);
		}
	}
	@Override
	public Trabajador getById(Integer id) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
				
				ps.setInt(1, id);
				
				try(ResultSet rs = ps.executeQuery()){
									
					if(rs.next()) {
						return new Trabajador(
								rs.getInt("idtrabajadores"), 
								rs.getString("nombre"), 
								rs.getString("apellidos"), 
								rs.getString("dni"));
					} else {
						return null;
					}
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener el Trabajador id: " + id, e);
		}
	}
	@Override
	public Integer insert(Trabajador trabajador){
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
				
				ps.setString(1, trabajador.getNombre());
				ps.setString(2, trabajador.getApellidos());
				ps.setString(3, trabajador.getDni());
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una insert");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al insertar el Trabajador", e);
		}
		return null; // alcambiar de void a integer el Dao me pide un return
	}
	@Override
	public void update(Trabajador trabajador) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
				
				ps.setString(1, trabajador.getNombre());
				ps.setString(2, trabajador.getApellidos());
				ps.setString(3, trabajador.getDni());
				ps.setInt(4, trabajador.getId());
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una update");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al modificar el Trabajador", e);
		}
	}
	@Override
	public void delete(Integer id) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_DELETE)) {
				
				ps.setInt(1, id);
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una delete");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al borrar el Trabajador", e);
		}
	}


}
