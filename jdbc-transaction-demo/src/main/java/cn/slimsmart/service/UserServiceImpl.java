package cn.slimsmart.service;

import cn.slimsmart.aop.transaction.JdbcTemplate;
import cn.slimsmart.aop.transaction.annotation.Transactional;

public class UserServiceImpl implements UserService{
	
	private JdbcTemplate jdbcTemplate;
	
	public UserServiceImpl(JdbcTemplate jdbcTemplate){
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Transactional
	public int save(String name, int age) {
		jdbcTemplate.insert("insert into user(id,name,age)values(1,'"+name+"',"+age+")");
		jdbcTemplate.insert("insert into user(id,name,age)values(2,'"+name+"',"+age+")");
		jdbcTemplate.insert("insert into user(id,name,age)values(1,'"+name+"',"+age+")");
		return 1;
	}
}
