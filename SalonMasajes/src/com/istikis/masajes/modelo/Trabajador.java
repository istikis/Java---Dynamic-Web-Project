package com.istikis.masajes.modelo;

public class Trabajador extends Persona {

	public Trabajador(Integer id, String nombre, String apellidos, String dni) {
		super(id, nombre, apellidos, dni);
		// TODO Auto-generated constructor stub
	}

	public Trabajador(String nombre, String apellidos, String dni) {
		super(null, nombre, apellidos, dni);
	}
	
	public Trabajador() {
		// TODO Auto-generated constructor stub
	}
}
