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
import com.istikis.masajes.modelo.Trabajador;

public class TrabajadorMySQL implements Dao<Trabajador> {

	private static final String SQL_SELECT = "SELECT * FROM trabajadores";
	private static final String SQL_SELECT_BY_ID = "SELECT * FROM trabajadores WHERE id=?";

	private static final String SQL_INSERT = "INSERT INTO trabajadores (idtrabajadores, nombre, apellidos, dni) VALUES (?,?,?,?)";
	private static final String SQL_UPDATE = "UPDATE trabajadores SET nombre=?, url=? WHERE id=?";
	private static final String SQL_DELETE = "DELETE FROM trabajadores WHERE id=?";
	
	private static String url, usuario, password;
	
	// SINGLETON
			private static TrabajadorMySQL instancia;
			
			private TrabajadorMySQL(String url, String usuario, String password) {
				TrabajadorMySQL.url = url;
				TrabajadorMySQL.usuario = usuario;
				TrabajadorMySQL.password = password;
				
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					throw new AccesoDatosException("No se ha encontrado el driver de MySQL");
				}
			}
			
			public static TrabajadorMySQL getInstancia(String pathConfiguracion) {
				try {
					if (instancia == null) {
						Properties configuracion = new Properties();
						configuracion.load(new FileInputStream(pathConfiguracion));

						instancia = new TrabajadorMySQL(configuracion.getProperty("mysql.url"),
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
	public Iterable<Trabajador> obtenerTodos() {
		try (Connection con = getConexion()) {
			try(PreparedStatement ps = con.prepareStatement(SQL_SELECT)) {
				try(ResultSet rs = ps.executeQuery()){
					
					ArrayList<Trabajador> trabajadores = new ArrayList<>();
					
					while(rs.next()) {
						trabajadores.add(new Trabajador(
								rs.getLong("idtrabajadores"), 
								rs.getString("nombre"), 
								rs.getString("apellidos"),
								rs.getString("dni")
								));
					}
					
					return trabajadores;
				}
			}
		} catch (SQLException e) {
			throw new AccesoDatosException("Error al obtener todos los clientes", e);
		}
	}
	@Override
	public Trabajador obtenerPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void agregar(Trabajador objeto) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void modificar(Trabajador objeto) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void borrar(Long id) {
		// TODO Auto-generated method stub
		
	}
}
