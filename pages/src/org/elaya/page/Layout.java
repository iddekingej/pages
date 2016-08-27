package org.elaya.page;


public abstract class Layout extends PageElement<LayoutThemeItem> {

	public Layout() {
		super();
	}

	
	public void display(Object p_value) throws Exception
	{
		display();
	}
	
	public String getThemeName() {
		return "LayoutThemeItem";
	}

}
