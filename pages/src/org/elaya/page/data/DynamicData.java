package org.elaya.page.data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicData implements Dynamic {

	@Override
	public Object get(String pname) throws NoSuchFieldException,   IllegalAccessException {
		Field field=getClass().getField(pname);
		return field.get(this);
	}

	@Override
	public void put(String name, Object value) throws NoSuchFieldException, NoSuchMethodException,  IllegalAccessException,  InvocationTargetException {
		Field field;
		try{
			field=getClass().getField(name);
		} catch(java.lang.NoSuchFieldException e){
			throw new  java.lang.NoSuchFieldException(e.getMessage()+"(Classname="+getClass().getName()+")");
		}
		Class<?> className=field.getType();
		Object usedValue=value;
		if((value !=null) && (!field.getType().isInstance(value))){
			Method method=className.getMethod("valueOf", value.getClass());
			usedValue=method.invoke(null, value);
		}
		
		field.set(this,usedValue);
		
	}

	@Override
	public boolean containsKey(String pname) {
		try{
			getClass().getField(pname);
		} catch(NoSuchFieldException e)
		{
			return false;
		}
		return true;
	}

}
