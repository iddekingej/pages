package org.elaya.page.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;
import org.elaya.page.core.DynamicMethod.MethodNotFound;
import org.elaya.page.core.Dynamic.DynamicException;
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

		public static Method getMethod(Object object,String name) throws MethodNotFound{
			Objects.requireNonNull(object);
			for(Method method:object.getClass().getMethods()){
				if(method.getName().equals(name)){
					return method;
				}
			}
			throw new MethodNotFound(object,name);
		}		
		
		
		public static Object call(Object object,String methodName,Class<?>[] parameterTypes,Object[] parameters) throws NoSuchMethodException, IllegalAccessException,  InvocationTargetException
		{
				Method method=object.getClass().getMethod(methodName, parameterTypes);
				return method.invoke(object,parameters);
		}
		
		
		
		public static Object get(Object object,String name) throws DynamicException 
		{			
			String methodName="";
			try{		
				methodName="get"+name.substring(0,1).toUpperCase()+name.substring(1);
				Method method=getMethod(object,methodName);
				return method.invoke(object);		
			} catch(Exception e){
				throw new DynamicException("Getting "+name+" (through method '"+methodName+"')",e);
			}				
		}

		public static void put(Object object,String name, Object inValue) throws DynamicException 
		{
			String methodName="";
			try{
				methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);
				Method method=getMethod(object,methodName);
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
				throw new DynamicException("Setting '"+name+"' to '"+valueStr+"' (trough method "+methodName+")",e);
			}
		}	
		
		public static boolean containsKey(Object object,String name) {
			try{
				
				String methodName="get"+name.substring(0,1).toUpperCase()+name.substring(1);
				@SuppressWarnings("unused")
				Method method=DynamicObject.getMethod(object,methodName) ;
				return true;
			}catch(MethodNotFound e){
				return false;
			}
		}
		
}
