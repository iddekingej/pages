package org.elaya.page.data;

import java.sql.Connection;
import java.sql.SQLException;
import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.DataException;
import org.elaya.page.data.XMLBaseDataItem.XMLDataException;
import org.elaya.page.formula.FormulaParseException;
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
	 * @throws DataException 
	 * @throws ClassNotFoundException 
	 * @throws FormulaParseException 
	 */
	
	protected abstract  void processOwnData(MapData pdata) throws XMLDataException;
	
	public final MapData processData(MapData pdata) throws XMLDataException 
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
