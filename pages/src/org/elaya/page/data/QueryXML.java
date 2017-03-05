package org.elaya.page.data;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.elaya.page.core.DataException;
import org.elaya.page.core.DataRecord;
import org.elaya.page.core.DataRecordList;

public class QueryXML extends BaseQueryXML {

	/**
	 * Name of variable to
	 */
	private String name;
	
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getName()
	{
		return name;
	}


	@Override
	public void handleResult(ResultSet prs,MapData pdata) throws SQLException, DataException {
		// The result set is stored as a DataRecordList
		DataRecordList list=new DataRecordList(pdata);
		DataRecord record;
		pdata.put(getName(), list);
		ResultSetMetaData meta=prs.getMetaData();
		int numColumns=meta.getColumnCount();
		
		// Set result set column names
		for(int colCnt=1;colCnt<=numColumns;colCnt++){
			list.addField(meta.getColumnLabel(colCnt));
		}
		//Fill Data record list with data 
		while(prs.next()){
			record=list.addRecord();
			for(int colCnt=0;colCnt<numColumns;colCnt++){
				record.put(colCnt, prs.getObject(colCnt+1));
			}
		}		
	}

}
