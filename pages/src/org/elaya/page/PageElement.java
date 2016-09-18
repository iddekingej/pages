package org.elaya.page;


public abstract class PageElement<themeItem extends ThemeItemBase> extends Element<themeItem> {

	
	public boolean checkElement(Element<?> p_element){
		return true;
	}

	public PageElement()
	{
		super();
	}
	
}
