package org.elaya.page;

import org.elaya.page.data.Data;


public abstract class PageElement<themeItem extends ThemeItemBase> extends Element<themeItem> {
	private Data data;

	public Data getData()
	{
		return data;
	}
	
	final public void setData(Data p_data)
	{
		data=p_data;
	}
	
	final public boolean hasValue(String p_name) throws Exception
	{
		return data.contains(p_name);
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
