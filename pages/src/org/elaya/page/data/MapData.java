package org.elaya.page.data;

import java.util.HashMap;
import java.util.Map;

public class MapData extends Data{	
		private Object id;
		private MapData parent;
		private HashMap<String,Object> attributes=new HashMap<>();
		private HashMap<Object,Data> childeren=new HashMap<>();
		
		public int getSize()
		{
			int l_length=attributes.size();
			if(parent!=null)l_length +=parent.getSize();
			return l_length;
			
		}
		public MapData(Object p_id,MapData p_parent) {
			parent=p_parent;
			if(parent != null){
				parent.addToChilderen(this);
			}
			id=p_id;
		}
		
		public void setByMap(Map<String,Object> p_data)
		{
			attributes.putAll(p_data);
		}
		
		void addToChilderen(Data p_item)
		{
			childeren.put(p_item.getId(), p_item);
		}
		
		public Data getChild(Object p_id)
		{
			return childeren.get(p_id);
		}
		
		public Object getId(){ return id;}
		public Data getParent(){ return parent;}
		
		
		public Object get(String p_name){
			if(attributes.containsKey(p_name)){
				return attributes.get(p_name);
			}
			if(parent !=null){
				return parent.get(p_name);
			}
			return null;
		}
		public boolean containsKey(String p_name){
			if(attributes.containsKey(p_name)) return true;
			if(parent==null) return false;
			return parent.containsKey(p_name);
		}
		public void put(String p_name,Object p_value){
			attributes.put(p_name, p_value);
		}
		

}
