package org.elaya.page.reciever;

import org.elaya.page.data.DynamicMethod;

public class Parameter extends DynamicMethod {
	private String name;
	private ParameterType type;
	
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
