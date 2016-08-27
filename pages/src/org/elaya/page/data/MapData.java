package org.elaya.page.data;

import java.util.HashMap;
import java.util.Map;

public class MapData extends Data {
	private Map<String,Object> data;
	
	public MapData(Data p_parent)
	{
		super(p_parent);
		data=new HashMap<String,Object>();
	}
	
	public MapData(Data p_parent,Map<String,Object> p_map) {
		super(p_parent);
		data=p_map;
	}


	public void makeData() {

	}


	public Object get(String p_name) {
		return data.get(p_name);
	}

	public void put(String p_name,Object p_value){
		data.put(p_name,p_value);
	}

	public boolean contains(String p_name) {
		return data.containsKey(p_name);
	}
}
