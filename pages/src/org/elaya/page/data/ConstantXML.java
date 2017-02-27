package org.elaya.page.data;

public class ConstantXML extends XMLBaseDataItem {
	private String variable;
	private Object value;
	
	public void setVariable(String pvariable)
	{
		variable=pvariable;
	}
	
	public String getVariable()
	{
		return variable;
	}
	
	public void setValue(String pvalue)
	{
		value=pvalue;
	}
	
	public void setObjectValue(Object pvalue)
	{
		value=pvalue;
	}
	
	public Object getValue()
	{ 
		return value; 
	}
	
	@Override
	public void processData(MapData pdata) {
		pdata.put(variable, value);
	}

}
