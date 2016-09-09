package org.elaya.page.reciever;

import org.elaya.page.data.DynamicMethod;

public class Parameter extends DynamicMethod {
	String name;
	ParameterType type;
	
	public void setName(String p_name)
	{
		name=p_name;
	}
	
	public String getName()
	{
		return name;
	}

	public ParameterType setType(ParameterType p_type)
	{
		return type;
	}
}
