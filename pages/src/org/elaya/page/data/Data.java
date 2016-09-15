package org.elaya.page.data;


abstract public class Data  {
	abstract public Object  getId();
	abstract public Data    getChild(Object p_object);
	abstract public Data    getParent();
	abstract public Object  get(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	abstract public void    put(String p_name,Object p_value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException;
	abstract public boolean containsKey(String p_name);
	abstract public int getSize();
	public String getString(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		Object l_value=get(p_name);
		if(l_value != null) return l_value.toString();
		return null;
	}
	
	public Integer getInteger(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
	{
		String l_value=getString(p_name);
		if(l_value != null) return Integer.valueOf(l_value);
		return null;
	}
	
}
