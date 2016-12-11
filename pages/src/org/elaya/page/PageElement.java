package org.elaya.page;


public abstract class PageElement<themeItem extends ThemeItemBase> extends Element<themeItem> {

	public PageElement()
	{
		super();
	}
	
	@Override
	public boolean checkElement(Element<?> p_element){
		return true;
	}

	
}
 