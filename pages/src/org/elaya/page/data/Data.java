package org.elaya.page.data;


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
	
	public static class KeyNotFoundException extends Exception{
		private static final long serialVersionUID = 8614663798348780748L;
		
		public KeyNotFoundException(String message){
			super(message);
		}
	}
	
	
	public abstract Object  getId();
	public abstract Data    getChild(Object pobject);
	public abstract Data    getParent();
	public abstract Object  get(String pname) throws KeyNotFoundException;
	public abstract void    put(String pname,Object pvalue) ;
	public abstract boolean containsKey(String pname);
	public abstract int     getSize();
	
	public String getString(String pname) throws KeyNotFoundException  {
		Object value=get(pname);
		if(value != null){
			return value.toString();
		}
		return null;
	}
	
	public Integer getInteger(String pname) throws KeyNotFoundException  
	{
		String value=getString(pname);
		if(value != null){
			return Integer.valueOf(value);
		}
		return null;
	}
	
}
