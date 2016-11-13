package org.elaya.page.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.Objects;

import org.elaya.page.data.DynamicMethod.methodNotFound;

public class DynamicObject {

		public static class DynamicError extends Exception{

			private static final long serialVersionUID = 5084017331992006760L;
			
			public DynamicError(String p_message){
				super(p_message);
			}
			
		}
		public static Object createObjectFromName(String p_name,LinkedList<String> p_errors) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException
		{
			Objects.requireNonNull(p_name);
			return createObjectFromName(p_name,new Class<?>[]{},new Object[]{}, p_errors);
		}
		
		public static Constructor<?> getConstructorByName(String p_name,Class<?>[] p_types,LinkedList<String> p_errors) throws NoSuchMethodException, ClassNotFoundException
		{
			Class<?> l_class;		
			try{
				
				l_class=Class.forName(p_name);			
			}catch(ClassNotFoundException l_e){				
				if(p_errors !=null){
					p_errors.add("Class "+p_name+" not found");
					return null;
				}
				throw l_e;
			}
		 
			Constructor<?> l_constructor;
			try{
				l_constructor=l_class.getConstructor(p_types);
			} catch(NoSuchMethodException l_e){
				if(p_errors != null){
					p_errors.add("Object doesn't have a constructor with the given parameters");
					return null;
				}
				throw l_e;
			}
			return l_constructor;
		}
		public static Object createObjectFromName(String p_name,Class<?>[] p_types,Object[] p_params,LinkedList<String> p_errors) throws NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
			Constructor<?>l_constructor=getConstructorByName(p_name,p_types,p_errors);
			if(l_constructor != null){
				Object l_object=l_constructor.newInstance(p_params);
				return l_object;
			} else {
				return null;
			}
		}

		public static Method getMethod(Object p_object,String p_name) throws methodNotFound{
			Objects.requireNonNull(p_object);
			for(Method l_method:p_object.getClass().getMethods()){
				if(l_method.getName().toLowerCase().equals(p_name)){
					return l_method;
				}
			}
			throw new methodNotFound(p_object,p_name);
		}		
		
		public static Object get(Object p_object,String p_name) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, methodNotFound
		{			
			Method l_method=getMethod(p_object,"get"+p_name);
			return l_method.invoke(p_object);		
		}

		public static void put(Object p_object,String p_name, Object p_value) throws Exception
		{
			Method l_method=getMethod(p_object,"set"+p_name);
			Class<?> l_params[]=l_method.getParameterTypes();
			Object l_value=p_value;
			if(l_params.length>=1 && p_value != null){
				if(!l_params[0].getName().equals(p_value.getClass().getName())){
					
					Method l_methodConv=l_params[0].getMethod("valueOf",p_value.getClass());
					l_value=l_methodConv.invoke(null,l_value);
					
				}
			}
			l_method.invoke(p_object, l_value);		
		}	
		
		public static boolean containsKey(Object p_object,String p_name) {
			try{
				@SuppressWarnings("unused")
				Method l_method=DynamicObject.getMethod(p_object,"set"+p_name) ;
				return true;
			}catch(methodNotFound l_e){
				return false;
			}
		}
		
}
