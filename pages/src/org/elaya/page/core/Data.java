package org.elaya.page.core;


public abstract class Data  {
	
	public static class GettingValueException extends RuntimeException{
		private static final long serialVersionUID = 1L;
		public GettingValueException(String message,Throwable cause){
			super(message,cause);
		}
	}
	
	public static class SettingValueException extends RuntimeException{
		private static final long serialVersionUID = 1L;
		public SettingValueException(String message,Throwable cause){
			super(message,cause);
		}
	}
	
	public abstract Object  getId();
	public abstract Data    getChild(Object pobject);
	public abstract Data    getParent();
	public abstract Object  get(String key) throws KeyNotFoundException;
	public abstract void    put(String key,Object value) throws KeyNotFoundException ;
	public abstract boolean containsKey(String key);
	public abstract int     getSize();
	
	public String getString(String key) throws KeyNotFoundException  {
		Object value=get(key);
		if(value != null){
			return value.toString();
		}
		return null;
	}
	
	public Integer getInteger(String key) throws KeyNotFoundException  
	{
		String value=getString(key);
		if(value != null){
			return Integer.valueOf(value);
		}
		return null;
	}
	
	
	
}
