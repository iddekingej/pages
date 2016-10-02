package org.elaya.page.reciever;

import org.elaya.page.data.DynamicMethod;

public class Parameter extends DynamicMethod {
	private String name;
	private ParameterType type;
	
	public void setName(String p_name)
	{
		name=p_name;
	}
	
	public String getName()
	{
		return name;
	}

	public void setType(ParameterType p_type)
	{
		type=p_type;
	}
	
	public ParameterType getType()
	{
		return type;
	}
}
