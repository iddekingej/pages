package org.elaya.page.data;


abstract public class Data  {
	abstract public Object  getId();
	abstract public Data    getChild(Object pobject);
	abstract public Data    getParent();
	abstract public Object  get(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	abstract public void    put(String pname,Object pvalue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	abstract public boolean containsKey(String pname);
	abstract public int getSize();
	public String getString(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Object value=get(pname);
		if(value != null) return value.toString();
		return null;
	}
	
	public Integer getInteger(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		String value=getString(pname);
		if(value != null) return Integer.valueOf(value);
		return null;
	}
	
}
