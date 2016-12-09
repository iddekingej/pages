package org.elaya.page.data;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.elaya.page.application.Application;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

abstract public class DataModel {

	private Application application;
	
	public DataModel()
	{
	
	}
	
	public void setApplication(Application p_application){
		application=p_application;
	}
	public Application getApplication()
	{
		return application;
	}
	
	abstract protected void _processData(MapData p_data) throws Exception;
	
	public MapData	processData(MapData p_data) throws Exception
	{
		MapData l_data=new MapData(this,p_data);
		_processData(p_data);
		return l_data;
	}
	
	public DriverManagerDataSource getDB(String p_name)
	{
		return application.getDB(p_name); 
	}
	
	public DriverManagerDataSource getDefaultDB() throws Exception
	{
		return application.getDefaultDB();
	}

}
