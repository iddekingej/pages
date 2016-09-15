package org.elaya.page.data;

import java.lang.reflect.Field;

public class PropertiesData extends Data {

	

	@Override
	public Object get(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		Field l_field=getClass().getField(p_name);
		return l_field.get(this);
	}

	@Override
	public void put(String p_name, Object p_value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field l_field;
		try{
			l_field=getClass().getField(p_name);
		} catch(java.lang.NoSuchFieldException l_e){
			throw new  java.lang.NoSuchFieldException(l_e.getMessage()+"(Classname="+getClass().getName()+")");
		}
		l_field.set(this,p_value);
		
	}

	@Override
	public boolean containsKey(String p_name) {
		try{
			@SuppressWarnings("unused")
			Field l_field=getClass().getField(p_name);
		} catch(NoSuchFieldException l_e)
		{
			return false;
		}
		return true;
	}
	
	
	public PropertiesData() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getId() {
		return null;
	}

	@Override
	public Data getChild(Object p_object) {
		return null;
	}

	@Override
	public Data getParent() {
		return null;
	}


	@Override
	public int getSize() {
		return 1;
	}

}
