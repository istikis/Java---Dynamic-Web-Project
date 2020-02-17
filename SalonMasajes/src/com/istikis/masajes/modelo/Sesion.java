package com.istikis.masajes.modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Sesion {
	
	private Integer id;
	
	private Cliente cliente;
	private Trabajador trabajador;
	private Servicio servicio;
	
	private Date fecha;
	private String resena, calificacion;
	
	private boolean correcto = true;
	private String errorId, errorCliente, errorTrabajador, errorServicio, errorFecha, errorCalificacion, errorResena;

	public Sesion(String id, String cliente, String trabajador, String servicio, String fecha, String resena,
			String calificacion) {
		setId(id);
		setCliente(cliente);
		setTrabajador(trabajador);
		setServicio(servicio);
		setFecha(fecha);
		setResena(resena);
		setCalificacion(calificacion);
	}

	private void setFecha(String fecha) {
		try {
			setFecha(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(fecha));
		} catch (ParseException e) {
			setErrorFecha("El formato de la fecha es incorrecto");
		}
	}

	private void setServicio(String servicio) {
		if (servicio == null) {
			setErrorServicio("Debes seleccionar un servicio");
		} else {
			try {
				setServicio(new Servicio(Integer.parseInt(servicio), null, null));
			} catch (NumberFormatException e) {
				setErrorServicio("El id de servicio debe ser numérico");
			}
		}
	}

	private void setTrabajador(String trabajador) {
		if (trabajador == null) {
			setErrorTrabajador("Debes seleccionar un trabajador");
		} else {
			try {
				setTrabajador(new Trabajador(Integer.parseInt(trabajador), null, null, null));
			} catch (NumberFormatException e) {
				setErrorTrabajador("El id de trabajador debe ser numérico");
			}
		}
	}

	private void setCliente(String cliente) {
		if (cliente == null) {
			setErrorCliente("Debes seleccionar un cliente");
		} else {
			try {
				setCliente(new Cliente(Integer.parseInt(cliente), null, null, null));
			} catch (NumberFormatException e) {
				setErrorCliente("El id de cliente debe ser numérico");
			}
		}
	}

	private void setId(String id) {
		try {
			if (id == null || id.trim().length() == 0) {
				setId((Integer) null);
			} else {
				setId(Integer.parseInt(id));
			}
		} catch (NumberFormatException e) {
			setErrorId("El id de sesión debe ser numérico");
		}
	}
	
	public Sesion(Integer id, Cliente cliente, Trabajador trabajador, Servicio servicio, Date fecha, String resena,
			String calificacion) {
		
		setId(id);
		setCliente(cliente);
		setTrabajador(trabajador);
		setServicio(servicio);
		setFecha(fecha);
		setResena(resena);
		setCalificacion(calificacion);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Trabajador getTrabajador() {
		return trabajador;
	}
	public void setTrabajador(Trabajador trabajador) {
		this.trabajador = trabajador;
	}
	public Servicio getServicio() {
		return servicio;
	}
	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public String getResena() {
		return resena;
	}
	public void setResena(String resena) {
		this.resena = resena;
	}
	public String getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(String calificacion) {
		if (calificacion == null) {
			setErrorCalificacion("Calificación incorrecta");
		} else {
			switch (calificacion) {
			case "":
			case "No recomendable":
			case "Aceptable":
			case "Para repetir":
				break;
			default:
				setErrorCalificacion("Calificación incorrecta");
			}
		}
		
		this.calificacion = calificacion;
	}
	public boolean isCorrecto() {
		return correcto;
	}

	public void setCorrecto(boolean correcto) {
		this.correcto = correcto;
	}

	public String getErrorId() {
		return errorId;
	}

	public void setErrorId(String errorId) {
		setCorrecto(false);
		this.errorId = errorId;
	}

	public String getErrorCliente() {
		return errorCliente;
	}

	public void setErrorCliente(String errorCliente) {
		setCorrecto(false);
		this.errorCliente = errorCliente;
	}

	public String getErrorTrabajador() {
		return errorTrabajador;
	}

	public void setErrorTrabajador(String errorTrabajador) {
		setCorrecto(false);
		this.errorTrabajador = errorTrabajador;
	}

	public String getErrorServicio() {
		return errorServicio;
	}

	public void setErrorServicio(String errorServicio) {
		setCorrecto(false);
		this.errorServicio = errorServicio;
	}

	public String getErrorFecha() {
		return errorFecha;
	}

	public void setErrorFecha(String errorFecha) {
		setCorrecto(false);
		this.errorFecha = errorFecha;
	}

	public String getErrorCalificacion() {
		return errorCalificacion;
	}

	public void setErrorCalificacion(String errorCalificacion) {
		this.errorCalificacion = errorCalificacion;
	}

	public String getErrorResena() {
		return errorResena;
	}

	public void setErrorResena(String errorResena) {
		setCorrecto(false);
		this.errorResena = errorResena;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calificacion == null) ? 0 : calificacion.hashCode());
		result = prime * result + ((cliente == null) ? 0 : cliente.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((resena == null) ? 0 : resena.hashCode());
		result = prime * result + ((servicio == null) ? 0 : servicio.hashCode());
		result = prime * result + ((trabajador == null) ? 0 : trabajador.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		if (calificacion == null) {
			if (other.calificacion != null)
				return false;
		} else if (!calificacion.equals(other.calificacion))
			return false;
		if (cliente == null) {
			if (other.cliente != null)
				return false;
		} else if (!cliente.equals(other.cliente))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (resena == null) {
			if (other.resena != null)
				return false;
		} else if (!resena.equals(other.resena))
			return false;
		if (servicio == null) {
			if (other.servicio != null)
				return false;
		} else if (!servicio.equals(other.servicio))
			return false;
		if (trabajador == null) {
			if (other.trabajador != null)
				return false;
		} else if (!trabajador.equals(other.trabajador))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Sesion [id=" + id + ", cliente=" + cliente + ", trabajador=" + trabajador + ", servicio=" + servicio
				+ ", fecha=" + fecha + ", resena=" + resena + ", calificacion=" + calificacion + "]";
	}
}
