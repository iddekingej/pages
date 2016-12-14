package org.elaya.page.data;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.data.DynamicMethod.methodNotFound;

public interface Dynamic {
	public Object get(String pname) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, methodNotFound;
	public void put(String pname,Object pvalue) throws Exception, Throwable;
	public boolean containsKey(String pname);
}
