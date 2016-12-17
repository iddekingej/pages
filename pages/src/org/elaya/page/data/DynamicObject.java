package org.elaya.page.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.elaya.page.data.Dynamic.DynamicException;
import org.elaya.page.data.DynamicMethod.MethodNotFound;

public class DynamicObject {

	private DynamicObject()
	{
		
	}
		public static class DynamicError extends Exception{

			private static final long serialVersionUID = 5084017331992006760L;
			
			public DynamicError(String pmessage){
				super(pmessage);
			}
			
		}
		
		public static Object createObjectFromName(String pname) throws InstantiationException, IllegalAccessException,  InvocationTargetException, NoSuchMethodException,  ClassNotFoundException
		{
			Objects.requireNonNull(pname);
			return createObjectFromName(pname,new Class<?>[]{},new Object[]{});
		}
		
		public static Constructor<?> getConstructorByName(String pname,Class<?>[] ptypes) throws NoSuchMethodException, ClassNotFoundException
		{
			Class<?> className;		
			className=Class.forName(pname);			
			Constructor<?> constructor;
			constructor=className.getConstructor(ptypes);
			return constructor;
		}
		public static Object createObjectFromName(String pname,Class<?>[] ptypes,Object[] pparams) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException,  InvocationTargetException  {
		
			Constructor<?>constructor=getConstructorByName(pname,ptypes);
			if(constructor != null){
				return constructor.newInstance(pparams);				
			} else {
				return null;
			}
		}

		public static Method getMethod(Object pobject,String pname) throws MethodNotFound{
			Objects.requireNonNull(pobject);
			for(Method method:pobject.getClass().getMethods()){
				if(method.getName().toLowerCase().equals(pname)){
					return method;
				}
			}
			throw new MethodNotFound(pobject,pname);
		}		
		
		public static Object get(Object pobject,String pname) throws DynamicException 
		{			
			try{		
				Method method=getMethod(pobject,"get"+pname);
				return method.invoke(pobject);		
			} catch(Exception e){
				throw new DynamicException("Getting "+pname,e);
			}				
		}

		public static void put(Object object,String name, Object inValue) throws DynamicException 
		{
			try{
				Method method=getMethod(object,"set"+name);
				Class<?>[] params=method.getParameterTypes();
				Object value=inValue;
				if(params.length>=1 && value != null && !params[0].isInstance(value)){
					Method methodConv=params[0].getMethod("valueOf",value.getClass());
					value=methodConv.invoke(null,value);				
				}
				method.invoke(object, value);
			}  catch(Exception e){
				String valueStr;
				if(inValue != null){
					valueStr=inValue.toString();
				} else {
					valueStr="<null>";
				}
				throw new DynamicException("Setting '"+name+"' to '"+valueStr+"'",e);
			}
		}	
		
		public static boolean containsKey(Object pobject,String pname) {
			try{
				@SuppressWarnings("unused")
				Method method=DynamicObject.getMethod(pobject,"set"+pname) ;
				return true;
			}catch(MethodNotFound e){
				return false;
			}
		}
		
}
