package org.elaya.page.spring;

import java.sql.Connection;
import java.sql.SQLException;

import org.elaya.page.security.AbstractDBAuthenticator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class SpringDBAuthenticator extends AbstractDBAuthenticator implements ApplicationContextAware {
	ApplicationContext applicationContext;
	String connectionName;

	public SpringDBAuthenticator() {
		// TODO Auto-generated constructor stub
	}
	
	public void setConnectionName(String p_connectionName)
	{
		connectionName=p_connectionName;
	}
	
	public String getConnectionName()
	{
		return connectionName;
	}
	
	@Override
	protected Connection getConnection() throws ClassNotFoundException, SQLException {
		DriverManagerDataSource l_db=applicationContext.getBean(connectionName,DriverManagerDataSource.class);
		return l_db.getConnection();
	}

	public ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}
	
	@Override
	public void setApplicationContext(ApplicationContext p_applicationContext) throws BeansException {
		applicationContext=p_applicationContext;
	}

}
