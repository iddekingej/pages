package org.elaya.page.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicMethod implements Dynamic{
	public static class methodNotFound extends Exception{
		private static final long serialVersionUID = 3175898698076086965L;
		
		public methodNotFound(Object p_object,String p_name){
			super("Method not found :"+p_object.getClass().getName()+"."+p_name);
		}
	}
	
	public DynamicMethod() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Object get(String p_name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, methodNotFound
	{			
		return DynamicObject.get(this, p_name);		
	}

	@Override
	public void put(String p_name, Object p_value) throws Exception
	{
		DynamicObject.put(this,p_name,p_value);
	}

	@Override
	public boolean containsKey(String p_name) {
		try{
			@SuppressWarnings("unused")
			Method l_method=DynamicObject.getMethod(this,"set"+p_name) ;
			return true;
		}catch(methodNotFound l_e){
			return false;
		}
	}

}
