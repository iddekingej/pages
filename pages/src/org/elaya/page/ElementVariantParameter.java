package org.elaya.page;
/**
 * When an element variant is used only the properties of the object created by the 
 * top node can be set. When using parameter also properties of objects created by the
 * child nodes can be set
 */
public class ElementVariantParameter implements NamedObject{
	/**
	 * The parameter is uniquely defined by it's name
	 */
	private String name="";
	/**
	 * Default value, used when the parameter is not present.
	 * When default value=null then the parameter is mandatory.
	 */
	private String defaultValue=null;
	
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
