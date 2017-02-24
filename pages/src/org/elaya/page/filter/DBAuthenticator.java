package org.elaya.page.filter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;


public class DBAuthenticator extends AbstractDBAuthenticator implements PageApplicationAware {

	private String connectionName;
	private Application application;
	
	@Override
	public Application getApplication() {
		return application;
	}

	@Override
	public void setApplication(Application papplication) {
		application=papplication;
	}
	
	public String getConnectionName()
	{
		return connectionName;
	}
	
	public void setConnectionName(String pconnectionName)
	{
		connectionName=pconnectionName;
	}
	
	@Override	
	protected Connection getConnection() throws ClassNotFoundException, SQLException
	{
		return application.connectToDB(connectionName);
	}


	
	

}
