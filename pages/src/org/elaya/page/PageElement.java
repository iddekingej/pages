package org.elaya.page;


public abstract class PageElement<themeItem> extends Element<themeItem> {
	
	
	public void display(String p_value) throws Exception{
			throw new Exception("Display(String) Shouldn't be used from page element");
	};

}
