package org.elaya.page.filter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.elaya.page.core.PageSession;

public abstract class AbstractDBAuthenticator implements Authenticator {

	private String parameterOrder;
	private String[] parameterOrderSplit;
	private String sql; 
	
	public void setSql(String psql)
	{
		sql=psql;
	}
	
	public String getSql()
	{
		return sql;
	}

	public void setParameterOrder(String porder)
	{
		parameterOrder=porder;
		parameterOrderSplit=porder.trim().split(",");
	}
	
	public String getParameterOrder()
	{
		return parameterOrder;
	}
	
	
	protected  abstract Connection getConnection() throws ClassNotFoundException, SQLException;

	@Override
	public Map<String, Object> getAuthenicate(PageSession session) throws SQLException, ClassNotFoundException {
		Map<String,Object> returnValue=null;
		Connection connection=getConnection();
		PreparedStatement statement=connection.prepareStatement(sql);		
		String parameter;
		for(int cnt=0;cnt<parameterOrderSplit.length;cnt++){
			parameter=parameterOrderSplit[cnt];
			statement.setString(cnt+1, session.getParameter(parameter));	
		}
		ResultSet set=statement.executeQuery();
		if(set.next()){
			returnValue=new HashMap<>();
			ResultSetMetaData meta=set.getMetaData();
			String colName;
			int num=meta.getColumnCount();
			for(int colCnt=0;colCnt<num;colCnt++){
				colName=meta.getColumnName(colCnt+1);
				returnValue.put(colName, set.getObject(colCnt+1));
			}
		}
		statement.close();
		return returnValue;
	}

}
