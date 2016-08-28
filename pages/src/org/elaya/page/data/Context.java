package org.elaya.page.data;

import java.util.HashMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Context {

	private ApplicationContext dbContext;
	HashMap<String,DriverManagerDataSource> dbConnections=new HashMap<String,DriverManagerDataSource>();
	public Context() {
		dbContext=new ClassPathXmlApplicationContext("../database/database.xml");
	}
  	
	public DriverManagerDataSource getDb(String p_name)
	{
		if(dbConnections.containsKey(p_name)){
			return dbConnections.get(p_name);
		}
		DriverManagerDataSource l_db=(DriverManagerDataSource)dbContext.getBean(p_name);
		dbConnections.put(p_name, l_db);
		return l_db;
	}
	
  	
}
