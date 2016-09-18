package org.elaya.page.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;

public class DynamicObject {

		public static class DynamicError extends Exception{

			private static final long serialVersionUID = 5084017331992006760L;
			
			public DynamicError(String p_message){
				super(p_message);
			}
			
		}
		public static Object createObjectFromName(String p_name,LinkedList<String> p_errors) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException
		{
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
	

}
