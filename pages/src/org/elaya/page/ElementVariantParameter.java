package org.elaya.page;

public class ElementVariantParameter implements NamedObject{
	private String name="";
	private String defaultValue="";
	
	public void setName(String pname)
	{
		name=pname;
	}
	
	@Override
	public String getName()
	{
		return name;
	}
	
	@Override
	public String getFullName()
	{
		return name;
	}
	
	public void setDefaultValue(String pdefaultValue)
	{
		defaultValue=pdefaultValue;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
}
