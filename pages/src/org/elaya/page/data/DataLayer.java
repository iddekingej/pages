package org.elaya.page.data;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.xml.parsers.ParserConfigurationException;
import org.elaya.page.Errors.AliasNotFound;
import org.elaya.page.Errors.InvalidTypeException;
import org.elaya.page.Errors.LoadingAliasFailed;
import org.elaya.page.application.Application;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.application.Application.InvalidAliasType;
import org.elaya.page.core.Data.KeyNotFoundException;
import org.elaya.page.application.PageApplicationAware;
import org.xml.sax.SAXException;
/**
 * Interface page request, page widget and other data components
 *  (like models, database etc)
 *
 */
public abstract  class DataLayer implements PageApplicationAware {
/**
 * Central application object
 */
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
	
	/**
	 * The method is used to  process data used in page widgets
	 * pdata is a data container used in the page widget. It contains
	 * Already data from the upper layer. It can be used or changed by 
	 * this method. New data must be added to this data container.
	 * 
	 * @param pdata Data used in the page widget 
	 * @throws InvalidTypeException 
	 */
	
	protected abstract  void processOwnData(MapData pdata) throws SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, KeyNotFoundException, InvalidTypeException ;
	
	public final MapData processData(MapData pdata) throws SQLException, DefaultDBConnectionNotSet, ParserConfigurationException, SAXException, IOException, InvalidAliasType, AliasNotFound, LoadingAliasFailed, KeyNotFoundException, InvalidTypeException
	{
		MapData data=new MapData(this,pdata);
		processOwnData(data);
		return data;
	}
	
	/**
	 * Get an database connection by name
	 * 
	 * @param pname
	 * @return 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public Connection getConnection(String pname) throws ClassNotFoundException, SQLException
	{
		return application.connectToDB(pname); 
	}
	
	/**
	 * Get the default database connection
	 * 
	 * @return JDBC Connection
	 */
	
	public Connection getDefaultDB() throws ClassNotFoundException, SQLException  
	{
		return application.connectToDefaultDB();
	}

}
