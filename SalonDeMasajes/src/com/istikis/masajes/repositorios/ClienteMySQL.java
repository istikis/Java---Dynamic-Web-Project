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
import com.istikis.masajes.modelo.Cliente;

public class ClienteMySQL implements Dao<Cliente>{
	
	private static final String SQL_SELECT = "SELECT * FROM clientes";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM clientes WHERE id=?";

	private static final String SQL_INSERT = "INSERT INTO clientes (nombre, apellidos, dni) VALUES (?,?)";
	private static final String SQL_UPDATE = "UPDATE clientes SET nombre=?, apellidos=?, dni=? WHERE idclientes=?";
	private static final String SQL_DELETE = "DELETE FROM clientes WHERE idclientes=?";

	private static String url, usuario, password;
	
	// SINGLETON
		private static ClienteMySQL instancia;
		
		private ClienteMySQL(String url, String usuario, String password) {
			ClienteMySQL.url = url;
			ClienteMySQL.usuario = usuario;
			ClienteMySQL.password = password;
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				throw new AccesoDatosException("No se ha encontrado el driver de MySQL");
			}
		}
		
		public static ClienteMySQL getInstancia(String pathConfiguracion) {
			try {
				if (instancia == null) {
					Properties configuracion = new Properties();
					configuracion.load(new FileInputStream(pathConfiguracion));

					instancia = new ClienteMySQL(configuracion.getProperty("mysql.url"),
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
	public Iterable<Cliente> obtenerTodos() {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
				try(ResultSet rs = ps.executeQuery()){
					ArrayList<Cliente> clientes = new ArrayList<>();
					
					while(rs.next()) {
						clientes.add(new Cliente(
								rs.getLong("idclientes"), 
								rs.getString("nombre"), 
								rs.getString("apellidos"),
								rs.getString("dni")
								));
					}
					
					return clientes;
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener todos los clientes", e);
		}
	}
	

	@Override
	public Cliente obtenerPorId(Long id) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT_BY_ID)) {
				ps.setLong(1, id);
				
				try(ResultSet rs = ps.executeQuery()){
									
					if(rs.next()) {
						return new Cliente(
								rs.getLong("idCliente"), 
								rs.getString("nombre"), 
								rs.getString("apellidos"),
								rs.getString("dni")
								);
					} else {
						return null;
					}
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener el video id: " + id, e);
		}
	}

	@Override
	public void agregar(Cliente cliente) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {
				
				ps.setString(1, cliente.getNombre());
				ps.setString(2, cliente.getApellidos());
				ps.setString(3, cliente.getDni());
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una insert");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al insertar cliente", e);
		}		
	}

	@Override
	public void modificar(Cliente cliente) {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_UPDATE)) {
				
				ps.setString(1, cliente.getNombre());
				ps.setString(2, cliente.getApellidos());
				ps.setString(3, cliente.getDni());
				
				int numeroRegistrosModificados = ps.executeUpdate();
				
				if(numeroRegistrosModificados != 1) {
					throw new AccesoDatosException("Se ha hecho más o menos de una update");
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al modificar el video", e);
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
			throw new AccesoDatosException("Error al borrar el cliente", e);
		}
		
	}

}
