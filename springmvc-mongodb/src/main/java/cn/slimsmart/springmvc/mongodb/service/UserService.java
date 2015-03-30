package cn.slimsmart.springmvc.mongodb.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.slimsmart.springmvc.mongodb.dao.MongoDBBaseDao;
import cn.slimsmart.springmvc.mongodb.entity.User;

@Service
public class UserService {
	
	@Autowired
	private MongoDBBaseDao mongoDBBaseDao;
	
	public User get(String id){
		return mongoDBBaseDao.findById(User.class, id);
	}

	public void save(User user) {
		mongoDBBaseDao.saveOrUpdate(user);
	}
}
