package org.elaya.page.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DynamicData implements Dynamic {

	@Override
	public Object get(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field=getClass().getField(pname);
		return field.get(this);
	}

	@Override
	public void put(String pname, Object pvalue) throws Throwable {
		Field field;
		try{
			field=getClass().getField(pname);
		} catch(java.lang.NoSuchFieldException e){
			throw new  java.lang.NoSuchFieldException(e.getMessage()+"(Classname="+getClass().getName()+")");
		}
		Class<?> className=field.getType();
		Object value=pvalue;
		if(value !=null){
			if(!className.getName().equals(pvalue.getClass().getName())){
				Method method=className.getMethod("valueOf", value.getClass());
				try{
					value=method.invoke(null, value);
				} catch(java.lang.reflect.InvocationTargetException e){
					throw e.getCause();
				}
			}
		}
		field.set(this,value);
		
	}

	@Override
	public boolean containsKey(String pname) {
		try{
			@SuppressWarnings("unused")
			Field field=getClass().getField(pname);
		} catch(NoSuchFieldException e)
		{
			return false;
		}
		return true;
	}

}
