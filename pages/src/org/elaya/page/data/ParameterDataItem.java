package org.elaya.page.data;

import org.elaya.page.Errors.InvalidTypeException;
import org.elaya.page.core.Data.KeyNotFoundException;
import org.elaya.page.core.PageSession;
import org.elaya.page.receiver.ParameterType;

public class ParameterDataItem implements XMLDataItem {

	private String name="";
	private String variable;
	private ParameterType parameterType;
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setVariable(String pvariable)
	{
		variable=pvariable;
	}
	
	public String getVariable()
	{
		return variable;
	}
	
	
	public void setParameterType(ParameterType pparameterType)
	{
		parameterType=pparameterType;
	}
	
	public ParameterType getParameterType()
	{
		return parameterType;
	}
	
	
	//TODO Check mandatory and handle errors when parsing to int or long failes
	@Override
	public void processData(MapData pdata) throws InvalidTypeException, KeyNotFoundException {
		PageSession pageSession=pdata.get("pageSession",PageSession.class);
		String realVarName=variable;
		if(realVarName==null){
			realVarName=name;
		}
		String value=pageSession.getParameter(name);
		Object resultValue=value;
		if(parameterType != null){
			switch(parameterType)
			{
				case STRING:
					break;
				case INTEGER:
					resultValue=Integer.valueOf(value);
					break;
				case LONGINT:
					resultValue=Long.valueOf(value);
					break;
				case BOOLEAN:
					resultValue=(value!= null) &&(!"".equals(value)||!"0".equals(value)||!"false".equals(value));
					break;
			}
		}
		pdata.put(realVarName,resultValue);
	}

}
