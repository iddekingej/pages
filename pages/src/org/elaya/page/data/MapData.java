package org.elaya.page.data;

import java.util.HashMap;
import java.util.Map;

public class MapData extends Data {
	private Map<String,Object> data=null;
	
	
	public MapData(Data p_parent)
	{
		super(p_parent);
	}
	
	
	public void setMap(Map<String,Object> p_data)
	{
		data=p_data;
	}
	
	public void init() throws Exception
	{	
		if(data==null) data=new HashMap<String,Object>();
		super.init();
	}
	
	public void makeData() throws Exception {

	}


	public Object get(String p_name) {
		if(data.containsKey(p_name)){
			return data.get(p_name);
		}
		return super.get(p_name);
	}
	


	public void put(String p_name,Object p_value){
		data.put(p_name,p_value);
	}

	public boolean contains(String p_name) {
		if(getParent()!=null){
			if(getParent().contains(p_name)) return true;
		}
		return data.containsKey(p_name);
	}
}
