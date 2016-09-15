package org.elaya.page.data;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;

abstract public class DataModel {

	private Context context;
	
	public DataModel(Context p_context)
	{
		context=p_context;
	}
	
	public Context getContext()
	{
		return context;
	}
	
	
	abstract protected void _processData(MapData p_data) throws SQLException, UnsupportedEncodingException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	
	public MapData	processData(MapData p_data) throws UnsupportedEncodingException, SQLException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		MapData l_data=new MapData(this,p_data);
		_processData(p_data);
		return l_data;
	}
	
}
