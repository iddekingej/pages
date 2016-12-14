package org.elaya.page.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.elaya.page.data.DynamicMethod.methodNotFound;

public class DynamicObject {

		public static class DynamicError extends Exception{

			private static final long serialVersionUID = 5084017331992006760L;
			
			public DynamicError(String pmessage){
				super(pmessage);
			}
			
		}
		public static Object createObjectFromName(String pname) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException
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
		public static Object createObjectFromName(String pname,Class<?>[] ptypes,Object[] pparams) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
			Constructor<?>constructor=getConstructorByName(pname,ptypes);
			if(constructor != null){
				Object object=constructor.newInstance(pparams);
				return object;
			} else {
				return null;
			}
		}

		public static Method getMethod(Object pobject,String pname) throws methodNotFound{
			Objects.requireNonNull(pobject);
			for(Method method:pobject.getClass().getMethods()){
				if(method.getName().toLowerCase().equals(pname)){
					return method;
				}
			}
			throw new methodNotFound(pobject,pname);
		}		
		
		public static Object get(Object pobject,String pname) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, methodNotFound
		{			
			Method method=getMethod(pobject,"get"+pname);
			return method.invoke(pobject);		
		}

		public static void put(Object pobject,String pname, Object pvalue) throws Exception
		{
			Method method=getMethod(pobject,"set"+pname);
			Class<?> params[]=method.getParameterTypes();
			Object value=pvalue;
			if(params.length>=1 && pvalue != null){
				if(!params[0].getName().equals(pvalue.getClass().getName())){
					
					Method methodConv=params[0].getMethod("valueOf",pvalue.getClass());
					value=methodConv.invoke(null,value);
					
				}
			}
			method.invoke(pobject, value);		
		}	
		
		public static boolean containsKey(Object pobject,String pname) {
			try{
				@SuppressWarnings("unused")
				Method method=DynamicObject.getMethod(pobject,"set"+pname) ;
				return true;
			}catch(methodNotFound e){
				return false;
			}
		}
		
}
