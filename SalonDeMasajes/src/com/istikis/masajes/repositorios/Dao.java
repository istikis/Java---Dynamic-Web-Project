package com.istikis.masajes.repositorios;

public interface Dao<T> {
	
	Iterable<T> obtenerTodos();
	T obtenerPorId(Long id);
	
	void agregar(T objeto);
	void modificar(T objeto);
	void borrar(Long id);

}
