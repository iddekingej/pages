package org.elaya.page.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.elaya.page.NamedObject;


public class DatabaseConnection implements NamedObject {
	String name;
	String username;
	String password;
	String url;
	String driverClassName;
	@Override
	public String getFullName() {
			return getName();
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String pusername)
	{
		username=pusername;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String ppassword)
	{
		password=ppassword;
	}
	public void setUrl(String purl)
	{
		url=purl;
	}
	
	public String getUrl()
	{
		return url;
	}

	public String getDriverClassName()
	{
		return driverClassName;
	}
	
	public void setDriverClassName(String pdriverClassName)
	{
		driverClassName=pdriverClassName;
	}
	
	@SuppressWarnings("static-access")
	public Connection connect() throws SQLException, ClassNotFoundException
	{
		getClass().forName(driverClassName);
	    Properties connectionProps = new Properties();
	    connectionProps.put("user",username);
	    if(password != null && !"".equals(password)){
	    	connectionProps.put("password",password);
	    }
	    return DriverManager.getConnection(url, connectionProps);
	}
}
