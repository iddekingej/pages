package org.elaya.page.data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DynamicMethod implements DynamicData{
	public static class methodNotFound extends Exception{
		private static final long serialVersionUID = 3175898698076086965L;
		
		public methodNotFound(Object p_object,String p_name){
			super("Method not found :"+p_object.getClass().getName()+"."+p_name);
		}
	}
	
	public DynamicMethod() {
		// TODO Auto-generated constructor stub
	}
	
	private Method getMethod(String p_name) throws methodNotFound{
		Method l_methods[]=this.getClass().getMethods();
		for(int l_cnt=0;l_cnt<l_methods.length;l_cnt++){
			if(l_methods[l_cnt].getName().toLowerCase().equals(p_name)){
				return l_methods[l_cnt];
			}
		}
		throw new methodNotFound(this,p_name);
	}

	
	@Override
	public Object get(String p_name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, methodNotFound
	{			
		Method l_method=getMethod("get"+p_name);
		return l_method.invoke(this);		
	}

	@Override
	public void put(String p_name, Object p_value) throws Exception
	{
		Method l_method=getMethod("set"+p_name);
		Class<?> l_params[]=l_method.getParameterTypes();
		Object l_value=p_value;
		if(l_params.length>=1 && p_value != null){
			if(!l_params[0].getName().equals(p_value.getClass().getName())){
				
				Method l_methodConv=l_params[0].getMethod("valueOf",p_value.getClass());
				l_value=l_methodConv.invoke(null,l_value);
				
			}
		}
		l_method.invoke(this, l_value);		
	}

	@Override
	public boolean containsKey(String p_name) {
		try{
			@SuppressWarnings("unused")
			Method l_method=getMethod("set"+p_name) ;
			return true;
		}catch(methodNotFound l_e){
			return false;
		}
	}

}
