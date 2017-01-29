package org.elaya.page.data;

import java.io.IOException;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.data.Data.KeyNotFoundException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.xml.sax.SAXException;

public abstract  class DataModel implements PageApplicationAware {

	private Application application;
	
	@Override
	public void setApplication(Application papplication){
		application=papplication;
	}
	
	@Override
	public Application getApplication()
	{
		return application;
	}
	
	protected abstract  void processOwnData(MapData pdata) throws SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, KeyNotFoundException ;
	
	public final MapData processData(MapData pdata) throws SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, KeyNotFoundException
	{
		MapData data=new MapData(this,pdata);
		processOwnData(data);
		return data;
	}
	
	public DriverManagerDataSource getDB(String pname)
	{
		return application.getDB(pname); 
	}
	
	public DriverManagerDataSource getDefaultDB() throws DefaultDBConnectionNotSet 
	{
		return application.getDefaultDB();
	}

}
