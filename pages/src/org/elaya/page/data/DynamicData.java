package org.elaya.page.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicData implements Dynamic {

	
	public DynamicData() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object get(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		// TODO Auto-generated method stub
		Field l_field=getClass().getField(p_name);
		return l_field.get(this);
	}

	@Override
	public void put(String p_name, Object p_value) throws Throwable {
		Field l_field;
		try{
			l_field=getClass().getField(p_name);
		} catch(java.lang.NoSuchFieldException l_e){
			throw new  java.lang.NoSuchFieldException(l_e.getMessage()+"(Classname="+getClass().getName()+")");
		}
		Class<?> l_class=l_field.getType();
		Object l_value=p_value;
		if(l_value !=null){
			if(!l_class.getName().equals(p_value.getClass().getName())){
				Method l_method=l_class.getMethod("valueOf", l_value.getClass());
				try{
					l_value=l_method.invoke(null, l_value);
				} catch(java.lang.reflect.InvocationTargetException l_e){
					throw l_e.getCause();
				}
			}
		}
		l_field.set(this,l_value);
		
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

}
