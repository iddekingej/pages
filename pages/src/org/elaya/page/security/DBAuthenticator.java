package org.elaya.page.security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletRequest;


public class DBAuthenticator extends Authenticator {

	public DBAuthenticator() {
		// TODO Auto-generated constructor stub
	}

	private String driverClass;
	private String url;
	private String password; 
	private String username;
	private String sql; 
	private String parameterOrder;
	private String[] parameterOrderSplit;
	private Connection connection;
	private PreparedStatement statement=null;

	public void setParameterOrder(String p_order)
	{
		parameterOrder=p_order;
		parameterOrderSplit=p_order.trim().split(",");
	}
	
	public String getParameterOrder()
	{
		return parameterOrder;
	}
	
	public void setDriverClass(String p_driverClass){
		driverClass=p_driverClass;
	}
	
	public String getDriverClass(){
		return driverClass;
	}
	
	public void setUrl(String p_url){
		url=p_url;
	}
	
	public String getUrl(){
		return url;
	}
	
	public void setPassword(String p_password){
		password=p_password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setUsername(String p_username)
	{
		username=p_username;
	}
	public String getUsername(){
		return username;
	}
	
	public void setSql(String p_sql)
	{
		sql=p_sql;
	}
	
	public String getSql()
	{
		return sql;
	}
	
	public void finalize() throws SQLException
	{
		if(statement != null) statement.close();
	}
	private void openConnection() throws SQLException, ClassNotFoundException
	{
		Class.forName(driverClass);
		connection=DriverManager.getConnection(url,username,password);
		statement=connection.prepareStatement(sql);

	}
	
	@Override
	public Map<String, Object> getAuthenicate(ServletRequest p_request) throws SQLException, ClassNotFoundException {
		if(connection==null)openConnection();
		String l_parameter;
		for(int l_cnt=0;l_cnt<parameterOrderSplit.length;l_cnt++){
			l_parameter=parameterOrderSplit[l_cnt];
			statement.setString(l_cnt+1, p_request.getParameter(l_parameter));	
		}
		ResultSet l_set=statement.executeQuery();
		if(l_set.next()){
			HashMap<String,Object> l_return=new HashMap<>();
			ResultSetMetaData l_meta=l_set.getMetaData();
			String l_colName;
			int l_num=l_meta.getColumnCount();
			for(int l_colCnt=0;l_colCnt<l_num;l_colCnt++){
				l_colName=l_meta.getColumnName(l_colCnt+1);
				l_return.put(l_colName, l_set.getObject(l_colCnt+1));
			}
			return l_return;
		}
		
		return null;
	}

}
