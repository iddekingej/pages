package org.elaya.page.spring;

import java.sql.Connection;
import java.sql.SQLException;

import org.elaya.page.security.AbstractDBAuthenticator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SpringDBAuthenticator extends AbstractDBAuthenticator implements ApplicationContextAware {
	private ApplicationContext applicationContext;
	private String connectionName;


	public void setConnectionName(String pconnectionName)
	{
		connectionName=pconnectionName;
	}
	
	public String getConnectionName()
	{
		return connectionName;
	}
	
	@Override
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		DriverManagerDataSource db=applicationContext.getBean(connectionName,DriverManagerDataSource.class);
		return db.getConnection();
	}

	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext papplicationContext) throws BeansException {
		applicationContext=papplicationContext;
	}

}
