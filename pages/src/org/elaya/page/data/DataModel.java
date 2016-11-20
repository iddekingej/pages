package org.elaya.page.data;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

import org.elaya.page.Application;
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
	
	public DriverManagerDataSource getDB(String p_name)
	{
		return application.getDB(p_name);
	}
	
	abstract protected void _processData(MapData p_data) throws SQLException, UnsupportedEncodingException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	
	public MapData	processData(MapData p_data) throws UnsupportedEncodingException, SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		MapData l_data=new MapData(this,p_data);
		_processData(p_data);
		return l_data;
	}
	

}
