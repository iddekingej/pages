package org.elaya.page.data;



import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

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
	
	protected abstract  void processOwnData(MapData pdata) throws Exception;
	
	public final MapData processData(MapData pdata) throws Exception
	{
		MapData data=new MapData(this,pdata);
		processOwnData(pdata);
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
