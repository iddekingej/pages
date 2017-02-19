package org.elaya.page.core;

import java.lang.reflect.Field;

public class PropertiesData extends Data {

	@Override
	public Object get(String name) throws GettingValueException {
		try{
			Field field=getClass().getField(name);
			return field.get(this);
		} catch(Exception e){
			throw new GettingValueException("Exception when getting field "+name+" of object of type "+getClass().getName(),e);
		}
	}

	@Override
	public void put(String name, Object value) throws SettingValueException {
		Field field;
		try{
			field=getClass().getField(name);
			field.set(this,value);
		} catch(Exception e){
			throw new SettingValueException("Exception when setting field "+name+" of object of type "+getClass().getName()+" to "+((value==null)?"Null":value.toString()),e);
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

	@Override
	public Object getId() {
		return null;
	}

	@Override
	public Data getChild(Object pobject) {
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
