package com.jalvaro.velolibrary.client.db;

import java.util.List;

import com.jalvaro.velolibrary.client.exceptions.VeloException;

public interface GenericInterfaceDAO<T> {
	
	 
	public long insert(T o) throws Exception;
	public long update(T o) throws Exception;
	public long insertAndUpdate(T o) throws Exception;
	public long deleteAll() throws VeloException;
	public long delete(String id) throws Exception;
	public List<T> getAll() throws Exception;
	public Object get(String id) throws Exception;
	public boolean enableBDD() throws Exception;
	public boolean closeBDD() throws Exception;
 
}
