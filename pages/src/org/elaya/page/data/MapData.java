package org.elaya.page.data;

import java.util.HashMap;
import java.util.Map;

import org.elaya.page.core.Data;

public class MapData extends Data{	
		private Object id;
		private MapData parent;
		private HashMap<String,Object> attributes=new HashMap<>();
		private HashMap<Object,Data> children=new HashMap<>();
		
		public MapData(Object pid,MapData parentData) {
			id=pid;
			parent=parentData;
			if(parent != null){
				parent.addToChildren(this);
			}
		}
		
		public MapData(){
			parent=null;
			id=null;
		}
		
		@Override
		public int getSize()
		{
			int length=attributes.size();
			if(parent!=null){
				length +=parent.getSize();
			}
			return length;
			
		}

		public void setByMap(Map<String,Object> pdata)
		{
			attributes.putAll(pdata);
		}
		
		public void addToChildren(Data pitem)
		{			
			children.put(pitem.getId(), pitem);
		}
		
		@Override
		public Data getChild(Object pid)
		{
			return children.get(pid);
		}
		
		@Override
		public Object getId(){ return id;}
		
		@Override
		public Data getParent(){ return parent;}
		
		@Override
		public Object get(String pname) throws KeyNotFoundException{
			if(attributes.containsKey(pname)){
				return attributes.get(pname);
			}
			if(parent !=null){
				return parent.get(pname);
			}
			throw new KeyNotFoundException(pname);
			
		}
		

		@Override
		public boolean containsKey(String pname){
			if(attributes.containsKey(pname)){
				return true;
			}
			if(parent==null){
				return false;
			}
			return parent.containsKey(pname);
		}
		
		@Override
		public void put(String pname,Object pvalue){
			attributes.put(pname, pvalue);
		}
		
		@Override
		public String toString()
		{
			return "[DataMap class="+getClass().getName()+" id="+((id==null)?"null":id.toString())+"]";
		}

}
