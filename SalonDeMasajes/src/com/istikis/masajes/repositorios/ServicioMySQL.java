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

import com.istikis.masajes.modelo.Servicio;

public class ServicioMySQL implements Dao<Servicio> {
	
	private static final String SQL_SELECT = "SELECT * FROM servicios";
	//private static final String SQL_SELECT_BY_ID = "SELECT * FROM servicios WHERE id=?";

	//private static final String SQL_INSERT = "INSERT INTO servicios (id, nombre, precio) VALUES (?,?,?)";
	//private static final String SQL_UPDATE = "UPDATE servicios SET nombre=?, url=? WHERE id=?";
	//private static final String SQL_DELETE = "DELETE FROM servicios WHERE id=?";
	
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void agregar(Servicio objeto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificar(Servicio objeto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void borrar(Long id) {
		// TODO Auto-generated method stub
		
	}

}


