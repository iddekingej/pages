package org.elaya.page;

import java.util.Map;


public abstract class PageElement<themeItem> extends Element<themeItem> {
	private Map<String,Object> data;

	public Map<String,Object> getData()
	{
		return data;
	}
	
	final public void setData(Map<String,Object> p_data)
	{
		data=p_data;
	}
	
	final public boolean hasValue(String p_name)
	{
		return data.containsKey(p_name);
	}

	final public Object getValue(String p_name)
	{
		return data.get(p_name);
	}
	
	
	public void display(Object p_value) throws Exception{
			throw new Exception("Display(String) Shouldn't be used from page element");
	};

	public PageElement()
	{
		super();
	}
	
}
