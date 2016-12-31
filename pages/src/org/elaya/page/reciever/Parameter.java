package org.elaya.page.reciever;

import org.elaya.page.data.DynamicMethod;

public class Parameter extends DynamicMethod {
	private String name;
	private ParameterType type;
	private boolean isMandatory;
	
	public void setIsMandatory(Boolean p_flag)
	{
		isMandatory=p_flag;
	}
	
	public boolean getIsMandatory()
	{
		return isMandatory;
	}
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	public String getName()
	{
		return name;
	}

	public void setType(ParameterType ptype)
	{
		type=ptype;
	}
	
	public ParameterType getType()
	{
		return type;
	}
}
