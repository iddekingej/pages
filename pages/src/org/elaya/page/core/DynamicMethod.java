package org.elaya.page.core;

public class DynamicMethod implements Dynamic{
	public static class MethodNotFound extends Exception{
		private static final long serialVersionUID = 3175898698076086965L;
		
		public MethodNotFound(Object pobject,String pname){
			super("Method not found :"+pobject.getClass().getName()+"."+pname);
		}
	}
	

	@Override
	public Object get(String pname) throws DynamicException  
	{			
		return DynamicObject.get(this, pname);		
	}

	@Override
	public void put(String pname, Object pvalue) throws DynamicException
	{
		DynamicObject.put(this,pname,pvalue);
	}

	@Override
	public boolean containsKey(String pname) {
		return DynamicObject.containsKey(this, pname);
	}

}
