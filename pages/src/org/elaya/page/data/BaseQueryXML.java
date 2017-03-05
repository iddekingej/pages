package org.elaya.page.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.DataException;

public abstract class BaseQueryXML extends XMLBaseDataItem implements PageApplicationAware {


	private Application application;
	/**
	 * Parameters, a comma separated list of variable name used as variable.
	 */
	private String[] parameters;
	
	/**
	 * Database connection used for querying.
	 * When null the default connection is used
	 */
	private String   connectionName;
	/**
	 * SQL Statement to execute
	 */
	private String   sql;
	
	
	public void setParameters(String pfields)
	{
		parameters=pfields.split(",");
	}
	
	public String[] getParameters()
	{
		return parameters;
	}
	
	public void setConnectionname(String pconnectionName)
	{
		connectionName=pconnectionName;
	}
	
	public String getConnectionName()
	{
		return connectionName;
	}
	
	public void setSql(String psql)
	{
		sql=psql;
	}
	
	public String getSql()
	{
		return sql;
	}
	
	@Override
	public void setApplication(Application papplication)
	{
		application=papplication;
	}
	
	@Override
	public Application getApplication()
	{
		return application;
	}

	public abstract void handleResult(ResultSet prs,MapData pdata) throws SQLException, DataException;
	
	@Override
	public void processData(MapData pdata) throws XMLDataException {
		try{
		Connection connection=application.connectToDB(connectionName);

		PreparedStatement statement=null;
		try{		
			statement=connection.prepareStatement(sql);
			if(getParameters() != null){
				int length=getParameters().length;
				for(int bp=1;bp<=length;bp++){
					statement.setObject(bp, pdata.get(parameters[bp-1]));				
				}
			}
			
			ResultSet rs=null;
			try{
				rs=statement.executeQuery();
				handleResult(rs,pdata);
			} 
			finally{
				if(rs != null){
					rs.close();
				}
			}
	
		}finally{
			if(statement != null){
				statement.close();
			}
		}
		}catch(Exception e)
		{
			throw new XMLDataException(e);
		}
	}

}
