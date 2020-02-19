package com.istikis.masajes.repositorios;

import java.util.Date;

import com.istikis.masajes.modelo.Sesion;

public interface SesionDao extends Dao<Sesion> {
	
	void citaPeriodicaSemanal(Integer idCliente, Integer idTrabajador, Integer idServicio, Date fechaInicial, int repeticiones);

}
