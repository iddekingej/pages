package org.elaya.page;


public abstract class PageElement<themeItem extends ThemeItemBase> extends Element<themeItem> {

	
	public void display(Object p_value) throws Exception{
			throw new Exception("Display(String) Shouldn't be used from page element");
	};
	
	public boolean checkElement(Element<?> p_element){
		return true;
	}

	public PageElement()
	{
		super();
	}
	
}
