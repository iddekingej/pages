package org.elaya.page.core;

public interface Dynamic {
	public static class DynamicException extends Exception
	{
		private static final long serialVersionUID = -6984593877293591548L;
		public DynamicException(String message,Throwable cause){
			super(message,cause);
		}
	}	
	public Object get(String pname) throws DynamicException;
	public void put(String pname,Object pvalue) throws DynamicException;
	public boolean containsKey(String pname);
}
