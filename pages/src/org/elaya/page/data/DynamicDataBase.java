package org.elaya.page.table;

import java.lang.reflect.Field;

public class DynamicDataBase implements DynamicData {

	public DynamicDataBase() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object get(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		Field l_field=getClass().getField(p_name);
		return l_field.get(this);
	}

	@Override
	public void put(String p_name, Object p_value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field l_field=getClass().getField(p_name);
		l_field.set(p_name,p_value);
		
	}

	@Override
	public boolean containsKey(String p_name) {
		try{
			Field l_field=getClass().getField(p_name);
		} catch(NoSuchFieldException l_e)
		{
			return false;
		}
		return true;
	}

}
