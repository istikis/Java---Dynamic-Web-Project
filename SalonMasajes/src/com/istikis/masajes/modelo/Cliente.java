package com.istikis.masajes.modelo;

public class Cliente extends Persona {

	public Cliente(Integer id, String nombre, String apellidos, String dni) {
		super(id, nombre, apellidos, dni);
		// TODO Auto-generated constructor stub
	}

	public Cliente(String nombre, String apellidos, String dni) {
		super(null, nombre, apellidos, dni);
	}
	
	public Cliente() {
		// TODO Auto-generated constructor stub
	}

}
