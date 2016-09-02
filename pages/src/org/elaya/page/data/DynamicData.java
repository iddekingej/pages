package org.elaya.page.data;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.data.DynamicMethod.methodNotFound;

public interface DynamicData {
	public Object get(String p_name) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, methodNotFound;
	public void put(String p_name,Object p_value) throws Exception;
	public boolean containsKey(String p_name);
}
