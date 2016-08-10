package org.elaya.page;

import java.util.HashMap;

public abstract class PageElement<themeItem> extends Element<themeItem> {
	
	abstract protected void setValues(HashMap<String,String> p_values);
	abstract public boolean hasValue(String p_name);
	abstract public String getValue(String p_name);
	
	public void display(String p_value) throws Exception{
			throw new Exception("Display(String) Shouldn't be used from page element");
	};

	public PageElement()
	{
		super();
	}
	
}
