package org.elaya.page.data;

import java.lang.reflect.Field;

public class PropertiesData extends Data {

	@Override
	public Object get(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field=getClass().getField(pname);
		return field.get(this);
	}

	@Override
	public void put(String pname, Object pvalue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
		try{
			field=getClass().getField(pname);
		} catch(java.lang.NoSuchFieldException e){
			throw new  java.lang.NoSuchFieldException(e.getMessage()+"(Classname="+getClass().getName()+")");
		}
		field.set(this,pvalue);
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
