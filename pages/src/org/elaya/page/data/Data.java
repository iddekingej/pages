package org.elaya.page.data;


public abstract class Data  {
	public abstract Object  getId();
	public abstract Data    getChild(Object pobject);
	public abstract Data    getParent();
	public abstract Object  get(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	public abstract void    put(String pname,Object pvalue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	public abstract boolean containsKey(String pname);
	public abstract int getSize();
	
	public String getString(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Object value=get(pname);
		if(value != null){
			return value.toString();
		}
		return null;
	}
	
	public Integer getInteger(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		String value=getString(pname);
		if(value != null){
			return Integer.valueOf(value);
		}
		return null;
	}
	
}
