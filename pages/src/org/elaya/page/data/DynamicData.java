package org.elaya.page.data;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class DynamicData implements Dynamic {
	

	
	@Override
	public Object get(String pname) throws DynamicException  {
		try{
			Field field=getClass().getField(pname);
			return field.get(this);
		} catch(Exception e){
			throw new DynamicException("Getting "+pname,e);
		}
	}

	@Override
	public void put(String name, Object value) throws DynamicException {
		Field field;
		try{
			field=getClass().getField(name);
			Class<?> className=field.getType();
			Object usedValue=value;
			if((value !=null) && (!field.getType().isInstance(value))){
				Method method=className.getMethod("valueOf", value.getClass());
				usedValue=method.invoke(null, value);
			}

			field.set(this,usedValue);
		} catch(Exception e){
			String valueStr;
			if(value != null){
				valueStr=value.toString();
			} else {
				valueStr="<null>";
			}
			throw new DynamicException("Setting '"+name+"' to '"+valueStr+"'",e);
		}
		
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
