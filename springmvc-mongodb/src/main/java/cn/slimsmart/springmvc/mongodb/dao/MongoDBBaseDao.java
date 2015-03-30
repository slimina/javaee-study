package cn.slimsmart.springmvc.mongodb.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import cn.slimsmart.springmvc.mongodb.entity.Page;

@Repository
public class MongoDBBaseDao implements BaseDao{
	
	@Autowired
	@Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate;

	@Override
	public <T> T findById(Class<T> entityClass, String id) {
		return this.mongoTemplate.findById(id, entityClass);
	}

	@Override
	public <T> List<T> findAll(Class<T> entityClass) {
		return this.mongoTemplate.findAll(entityClass);
	}

	@Override
	public <T> void remove(T obj) {
		this.mongoTemplate.remove(obj);
	}

	@Override
	public <T> void add(T obj) {
		this.mongoTemplate.insert(obj);
	}

	/**
	 * 要修改的Mongo对象
	 */
	@Override
	public <T> void saveOrUpdate(T obj) {
		this.mongoTemplate.save(obj);
	}
	
	
	/**
	 * 查询并分页
	 * 
	 * @param entityClass
	 *            对象类型
	 * @param query
	 *            查询条件
	 * @param page
	 *            分页
	 * @return
	 */
	public <T> List<T> findByQuery(Class<T> entityClass, Query query, Page page) {
		Long count = this.count(entityClass, query);
		page.setCount(count);
		int pageNumber = page.getCurrent();
		int pageSize = page.getPageCount();
		query.skip((pageNumber - 1) * pageSize).limit(pageSize);
		return this.mongoTemplate.find(query, entityClass);
	}
	
	/**
	 * @param entityClass 查询对象 
	 * @param query 查询条件
	 * @return
	 */
	public <T> Long count(Class<T> entityClass, Query query) {
		return this.mongoTemplate.count(query, entityClass);
	}
	
	/**
	 * 批量插入
	 * @param entityClass 对象类
	 * @param collection  要插入的对象集合
	 */
	public <T> void addCollection(Class<T> entityClass, Collection<T> collection){
		this.mongoTemplate.insert(collection, entityClass);
	}
	
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}

}
