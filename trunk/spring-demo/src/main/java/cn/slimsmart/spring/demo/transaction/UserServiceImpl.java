package cn.slimsmart.spring.demo.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Transactional
	public int save(String name, int age) throws Exception {
		insert(name, age);
		return 1;
	}
	
	public void insert(String name, int age){
		jdbcTemplate.update("insert into user(id,name,age)values(1,'"+name+"',"+age+")");
		jdbcTemplate.update("insert into user(id,name,age)values(2,'"+name+"',"+age+")");
		jdbcTemplate.update("insert into user(id,name,age)values(1,'"+name+"',"+age+")");
	}
}
