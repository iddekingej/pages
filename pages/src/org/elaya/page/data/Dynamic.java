package org.elaya.page.data;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.data.DynamicMethod.MethodNotFound;

public interface Dynamic {
	public Object get(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException,  MethodNotFound;
	public void put(String pname,Object pvalue) throws Exception;
	public boolean containsKey(String pname);
}
