package org.elaya.page.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicMethod implements Dynamic{
	public static class methodNotFound extends Exception{
		private static final long serialVersionUID = 3175898698076086965L;
		
		public methodNotFound(Object pobject,String pname){
			super("Method not found :"+pobject.getClass().getName()+"."+pname);
		}
	}
	
	public DynamicMethod() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object get(String pname) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, methodNotFound
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
			@SuppressWarnings("unused")
			Method method=DynamicObject.getMethod(this,"set"+pname) ;
			return true;
		}catch(methodNotFound e){
			return false;
		}
	}

}
