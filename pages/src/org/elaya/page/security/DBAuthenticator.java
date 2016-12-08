package org.elaya.page.security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBAuthenticator extends AbstractDBAuthenticator {

	public DBAuthenticator() {
		// TODO Auto-generated constructor stub
	}

	private String driverClass;
	private String url;
	private String password; 
	private String username;

	
	public void setDriverClass(String p_driverClass){
		driverClass=p_driverClass;
	}
	
	public String getDriverClass(){
		return driverClass;
	}
	
	public void setUrl(String p_url){
		url=p_url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setPassword(String p_password){
		password=p_password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setUsername(String p_username)
	{
		username=p_username;
	}
	public String getUsername(){
		return username;
	}
	

	
	protected Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName(driverClass);
		Connection l_connection=DriverManager.getConnection(url,username,password);
		return l_connection;
	}
	
	

}
