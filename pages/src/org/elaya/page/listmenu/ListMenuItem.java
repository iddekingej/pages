package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.ThemeItemBase;

abstract public class ListMenuItem<itemBase extends ThemeItemBase> extends Element<itemBase> {

	private String value;
	
	public void setValue(String p_value)
	{
		value=p_value;
	}
	
	public String getValue()
	{
		return value;
	}

	@Override
	final public void display(){
		
	}


}
