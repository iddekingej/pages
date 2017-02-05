package org.elaya.page.widget.listmenu;

import org.elaya.page.ThemeItemBase;
import org.elaya.page.widget.Element;

public abstract class ListMenuItem<T extends ThemeItemBase> extends Element<T> {

	private String value;
	
	public void setValue(String pvalue)
	{
		value=pvalue;
	}
	
	public String getValue()
	{
		return value;
	}



}
