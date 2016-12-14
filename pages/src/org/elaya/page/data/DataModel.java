package org.elaya.page.data;



import org.elaya.page.application.Application;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public abstract  class DataModel {

	private Application application;
	
	public DataModel()
	{
	
	}
	
	public void setApplication(Application papplication){
		application=papplication;
	}
	public Application getApplication()
	{
		return application;
	}
	
	protected abstract  void _processData(MapData pdata) throws Exception;
	
	public MapData	processData(MapData pdata) throws Exception
	{
		MapData data=new MapData(this,pdata);
		_processData(pdata);
		return data;
	}
	
	public DriverManagerDataSource getDB(String pname)
	{
		return application.getDB(pname); 
	}
	
	public DriverManagerDataSource getDefaultDB() throws Exception
	{
		return application.getDefaultDB();
	}

}
