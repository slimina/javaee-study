package cn.slimsmart.disconf.demo;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class MainMysqlTest {

	public static void main(String[] args) throws Exception {
		ApplicationContext  applicationContext = new ClassPathXmlApplicationContext(new String[] { "applicationContext-mysql.xml" });
		JdbcTemplate  jdbcTemplate = applicationContext.getBean(JdbcTemplate.class);
		while (true) {
			int count = jdbcTemplate.queryForInt("select count(1) from tb_order");
			System.out.println("count ===>"+count);
			Thread.sleep(3000);
		}
	}
}
