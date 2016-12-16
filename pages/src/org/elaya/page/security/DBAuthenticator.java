package org.elaya.page.security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBAuthenticator extends AbstractDBAuthenticator {

	private String driverClass;
	private String url;
	private String password; 
	private String username;

	
	public void setDriverClass(String pdriverClass){
		driverClass=pdriverClass;
	}
	
	public String getDriverClass(){
		return driverClass;
	}
	
	public void setUrl(String purl){
		url=purl;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setPassword(String ppassword){
		password=ppassword;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setUsername(String pusername)
	{
		username=pusername;
	}
	public String getUsername(){
		return username;
	}
	
	@Override	
	protected Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName(driverClass);
		return DriverManager.getConnection(url,username,password);	
	}
	
	

}
