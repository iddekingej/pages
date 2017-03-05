package org.elaya.page.data;

import org.elaya.page.core.PageSession;

public class ParameterXML extends XMLBaseDataItem {

	private String name="";
	private String variableName;
	private ParameterType parameterType;
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setVariableName(String pvariableName)
	{
		variableName=pvariableName;
	}
	
	public String getVariableName()
	{
		return variableName;
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
	public void processData(MapData pdata) throws XMLDataException{
		try{
			PageSession pageSession=pdata.get("pageSession",PageSession.class);
			String realVarName=variableName;
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
		} catch(Exception e){
			throw new XMLDataException(e);
		}
	}

}
