package com.istikis.masajes.repositorios;

public interface Dao<T> {
	
	Iterable<T> getAll();
	T getById(Integer id);
	
	Integer insert(T objeto);
	void update(T objeto);
	void delete(Integer id);


}
