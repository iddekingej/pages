package org.elaya.page.core;

import java.lang.reflect.InvocationTargetException;

import org.elaya.page.Errors.ReplaceVarException;

public abstract class Attribute {
	
	Class<?> type=null;

	public Attribute(Class<?> ptype)
	{
		type=ptype;
	}
	
	public Class<?> getType()
	{
		return type;
	}
	protected Object convertFromString(String pvalue) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		Class<?> cls =getType();		
    	return cls.getMethod("valueOf", String.class).invoke(null,pvalue);
	}
	

	public abstract Object getValue(Data pdata) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ReplaceVarException;
}
