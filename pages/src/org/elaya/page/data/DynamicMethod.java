package org.elaya.page.data;

import java.lang.reflect.InvocationTargetException;

public class DynamicMethod implements Dynamic{
	public static class MethodNotFound extends Exception{
		private static final long serialVersionUID = 3175898698076086965L;
		
		public MethodNotFound(Object pobject,String pname){
			super("Method not found :"+pobject.getClass().getName()+"."+pname);
		}
	}
	

	@Override
	public Object get(String pname) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, MethodNotFound
	{			
		return DynamicObject.get(this, pname);		
	}

	@Override
	public void put(String pname, Object pvalue) throws Exception
	{
		DynamicObject.put(this,pname,pvalue);
	}

	@Override
	public boolean containsKey(String pname) {
		try{			
			DynamicObject.getMethod(this,"set"+pname) ;
			return true;
		}catch(MethodNotFound e){
			return false;
		}
	}

}
