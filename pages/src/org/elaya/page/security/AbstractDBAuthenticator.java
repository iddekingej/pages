package org.elaya.page.security;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletRequest;

public abstract class AbstractDBAuthenticator extends Authenticator {

	private String parameterOrder;
	private String[] parameterOrderSplit;
	private String sql; 
	
	public void setSql(String p_sql)
	{
		sql=p_sql;
	}
	
	public String getSql()
	{
		return sql;
	}

	public void setParameterOrder(String p_order)
	{
		parameterOrder=p_order;
		parameterOrderSplit=p_order.trim().split(",");
	}
	
	public String getParameterOrder()
	{
		return parameterOrder;
	}
	
	
	protected  abstract Connection getConnection() throws ClassNotFoundException, SQLException;

	@Override
	public Map<String, Object> getAuthenicate(ServletRequest p_request) throws SQLException, ClassNotFoundException {
		Map<String,Object> l_return=null;
		Connection l_connection=getConnection();
		PreparedStatement l_statement=l_connection.prepareStatement(sql);		
		String l_parameter;
		for(int l_cnt=0;l_cnt<parameterOrderSplit.length;l_cnt++){
			l_parameter=parameterOrderSplit[l_cnt];
			l_statement.setString(l_cnt+1, p_request.getParameter(l_parameter));	
		}
		ResultSet l_set=l_statement.executeQuery();
		if(l_set.next()){
			l_return=new HashMap<>();
			ResultSetMetaData l_meta=l_set.getMetaData();
			String l_colName;
			int l_num=l_meta.getColumnCount();
			for(int l_colCnt=0;l_colCnt<l_num;l_colCnt++){
				l_colName=l_meta.getColumnName(l_colCnt+1);
				l_return.put(l_colName, l_set.getObject(l_colCnt+1));
			}
		}
		l_statement.close();
		return l_return;
	}

}
