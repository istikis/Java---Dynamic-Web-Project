package com.istikis.masajes.modelo;

import java.sql.Date;

public class Actuaciones {
	
	private Cliente Cliente;
	private Trabajador Trabajador;
	private Servicio Servicio;
	private Date fecha;
	private String resena;
	private String calificacion;

	public Actuaciones(Cliente cliente, Trabajador trabajador, Servicio servicio, Date fecha, String resena, String calificacion) {
		
		setCliente(cliente);
		setTrabajador(trabajador);
		setServicio(servicio);
		setFecha(fecha);
		setResena(resena);
		setCalificacion(calificacion);
	}

	public Actuaciones() {
		// TODO Auto-generated constructor stub
	}
	
	public Cliente getCliente() {
		return Cliente;
	}

	public void setCliente(Cliente cliente) {
		Cliente = cliente;
	}

	public Trabajador getTrabajador() {
		return Trabajador;
	}

	public void setTrabajador(Trabajador trabajador) {
		Trabajador = trabajador;
	}

	public Servicio getServicio() {
		return Servicio;
	}

	public void setServicio(Servicio servicio) {
		Servicio = servicio;
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
		this.calificacion = calificacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Cliente == null) ? 0 : Cliente.hashCode());
		result = prime * result + ((Servicio == null) ? 0 : Servicio.hashCode());
		result = prime * result + ((Trabajador == null) ? 0 : Trabajador.hashCode());
		result = prime * result + ((calificacion == null) ? 0 : calificacion.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((resena == null) ? 0 : resena.hashCode());
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
		Actuaciones other = (Actuaciones) obj;
		if (Cliente == null) {
			if (other.Cliente != null)
				return false;
		} else if (!Cliente.equals(other.Cliente))
			return false;
		if (Servicio == null) {
			if (other.Servicio != null)
				return false;
		} else if (!Servicio.equals(other.Servicio))
			return false;
		if (Trabajador == null) {
			if (other.Trabajador != null)
				return false;
		} else if (!Trabajador.equals(other.Trabajador))
			return false;
		if (calificacion == null) {
			if (other.calificacion != null)
				return false;
		} else if (!calificacion.equals(other.calificacion))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (resena == null) {
			if (other.resena != null)
				return false;
		} else if (!resena.equals(other.resena))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Actuaciones [Cliente=" + Cliente + ", Trabajador=" + Trabajador + ", Servicio=" + Servicio + ", fecha="
				+ fecha + ", resena=" + resena + ", calificacion=" + calificacion + "]";
	}


	
	

}
