package org.elaya.page.receiver;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.elaya.page.application.Application.DefaultDBConnectionNotSet;
import org.elaya.page.core.PageSession;
import org.elaya.page.core.Dynamic.DynamicException;


public class CRUDAction extends Action {
	private String pkField;
	private String tableName;
	private Map<String,String> fieldsMap=null;
	
	public void setFieldsMap(Map<String,String> pfieldsMap)
	{
		fieldsMap=pfieldsMap;
	}
	
	public Map<String,String>getFieldsMap(){
		return fieldsMap;
	}
	
	public void setPkField(String ppkField){
		pkField=ppkField;
	}
	
	public String getPkField(){
		return pkField;
	}
	
	public void setTableName(String ptableName){
		tableName=ptableName;
	}
	
	public String getTableName()
	{
		return tableName;
	}
	
	/***
	 * build insert data and execute it.
	 * 
	 *  @param data  Data used in insert statement
	 * @throws DynamicException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	
	private void insert(ReceiverData data) throws SQLException, DynamicException, ClassNotFoundException
	{
		Connection connection=getApplication().connectToDefaultDB();
		Set<String> fields=fieldsMap.keySet();
		fields.remove(pkField);
		String fieldList=StringUtils.join(fields,',');
		String buildlist="?"+StringUtils.repeat(",?",fields.size()-1);
		StringBuilder sqlCmd=new StringBuilder();
		sqlCmd.append("insert into ").append(tableName).append("(").append(fieldList);
		sqlCmd.append(") values(").append(buildlist).append(")");
		PreparedStatement stmt=connection.prepareStatement(sqlCmd.toString());
		try{
			int bindPos=1;
			for(String field:fields){
				stmt.setObject(bindPos, data.getData().get(fieldsMap.get(field)));
				bindPos++;
			}
			stmt.execute();
		} finally{
			stmt.close();
		}
	}
	
	private void edit(ReceiverData data) throws ClassNotFoundException, SQLException, DynamicException
	{
		Set<String> fields=fieldsMap.keySet();
		fields.remove(pkField);
		StringBuilder upd=new StringBuilder();
		upd.append("update ").append(tableName).append(" set ");
		boolean notFirst=false;
		for(String field:fields){			
			if(notFirst){
				upd.append(",");
			}
			notFirst=true;
			upd.append(field).append("=?");
		}
		upd.append(" where ").append(pkField).append("=?");
		Connection connection=getApplication().connectToDefaultDB();

		PreparedStatement stmt=connection.prepareStatement(upd.toString());
		try{
			int bindPos=1;
			for(String field:fields){
				stmt.setObject(bindPos, data.getData().get(fieldsMap.get(field)));
				bindPos++;
			}
			stmt.setObject(bindPos,data.getData().get(pkField));
			stmt.execute();
		} finally{
			stmt.close();
		}
	}
	
	private void delete(ReceiverData data) throws ClassNotFoundException, SQLException, DynamicException 
	{
		Connection connection=getApplication().connectToDefaultDB();
		PreparedStatement stmt=connection.prepareStatement("delete from "+tableName+" where "+pkField+"=?");
		try{	
			stmt.setObject(1,data.getData().get(pkField));
			stmt.execute();
		} finally{
			stmt.close();
		}
	}
	
	
	@Override	
	public void execute(Object object, PageSession psession, String cmd,ReceiverData data, Result result) throws ClassNotFoundException, SQLException, DynamicException{		
		if("add".equals(cmd)){			
			insert(data);
		} else 	if("delete".equals(cmd)){
			delete(data);
		} else if("edit".equals(cmd)){
			edit(data);
		}
	}

}
