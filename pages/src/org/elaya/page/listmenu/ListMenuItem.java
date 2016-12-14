package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.ThemeItemBase;

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
