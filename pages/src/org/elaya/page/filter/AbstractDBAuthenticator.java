package org.elaya.page.filter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.elaya.page.core.PageSession;
/**
 * Authorize the session by a database query.
 * When the query doesn't return a row, the password/user name is invalid or the
 * user is not authorized.
 * When the query returns a row the data is used to setup the session
 *
 */
public abstract class AbstractDBAuthenticator implements Authenticator {

	/**
	 * The query uses positional parameters. This property is a comma separated list 
	 * of parameter names for each parameter position.
	 * The parameters used are the post or get parameters of the request.
	 */
	private String parameterOrder;
	
	/**
	 * The query uses positional parameters. This property is a array 
	 * of parameter names for each parameter position.
	 * The parameters used are the post or get parameters of the request.
	 */
	private String[] parameterOrderSplit;
	
	/**
	 * Sql query to execute
	 */
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
	
	/**
	 * Get the connection to use to execute this query
	 *  
	 * @return
	 * 
	 */
	protected  abstract Connection getConnection() throws ClassNotFoundException, SQLException;

	@Override
	/**
	 * Parse query, set parameter by the post and get parameter and execute query.
	 * if the the query doesn't return a row , the authentication is failed.
	 * if the query returns a row , the result set is converted to a Map object.
	 */
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
