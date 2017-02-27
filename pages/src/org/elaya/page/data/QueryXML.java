package org.elaya.page.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import org.elaya.page.application.Application;
import org.elaya.page.application.PageApplicationAware;
import org.elaya.page.core.DataRecord;
import org.elaya.page.core.DataRecordList;

public class QueryXML extends XMLBaseDataItem implements PageApplicationAware {

	/**
	 * Name of variable to
	 */
	private String name;
	
	private Application application;
	/**
	 * Parameters
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
	
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getName()
	{
		return name;
	}
	
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
	
	@Override
	public void processData(MapData pdata) throws XMLDataException {
		try{
		Connection connection;
		if(connectionName ==null){
			connection=application.connectToDefaultDB();
		} else {
			connection=application.connectToDB(connectionName);
		}
		PreparedStatement statement=connection.prepareStatement(sql);;
		try{		
			if(parameters != null){
				for(int bp=0;bp<parameters.length;bp++){
					statement.setObject(bp, pdata.get(parameters[bp]));				
				}
			}
			ResultSet rs=statement.executeQuery();
			
			// The result set is stored as a DataRecordList
			DataRecordList list=new DataRecordList(pdata);
			DataRecord record;
			pdata.put(name, list);
			ResultSetMetaData meta=rs.getMetaData();
			int numColumns=meta.getColumnCount();
			
			// Set result set column names
			for(int colCnt=1;colCnt<=numColumns;colCnt++){
				list.addField(meta.getColumnLabel(colCnt));
			}
			//Fill Data record list with data 
			while(rs.next()){
				record=list.addRecord();
				for(int colCnt=0;colCnt<numColumns;colCnt++){
					record.put(colCnt, rs.getObject(colCnt+1));
				}
			}
			System.out.println("numr read="+list.getSize());
			rs.close();
	
		}finally{
			statement.close();
		}
		}catch(Exception e)
		{
			throw new XMLDataException(e);
		}
	}

}
