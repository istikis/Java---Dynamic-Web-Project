package com.istikis.masajes.controladores;

import com.istikis.masajes.modelo.Actuaciones;
import com.istikis.masajes.modelo.Cliente;
import com.istikis.masajes.modelo.Servicio;
import com.istikis.masajes.modelo.Trabajador;
import com.istikis.masajes.repositorios.Dao;

public class Globales {
	public static Dao<Actuaciones> daoActuaciones;
	public static Dao<Cliente> daoCliente;
	public static Dao<Trabajador> daoTrabajador;
	public static Dao<Servicio> daoServicio;
}
