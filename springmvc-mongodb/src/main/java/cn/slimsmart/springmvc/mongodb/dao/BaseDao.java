package cn.slimsmart.springmvc.mongodb.dao;

import java.util.List;

public interface BaseDao {

	<T> T findById(Class<T> entityClass, String id);

	<T> List<T> findAll(Class<T> entityClass);
	
	<T> void remove(T obj);
	<T> void add(T obj);
	<T> void saveOrUpdate(T obj);
}
