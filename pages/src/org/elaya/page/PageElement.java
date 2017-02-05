package org.elaya.page;


public abstract class PageElement<T extends ThemeItemBase> extends Element<T> {

	public PageElement()
	{
		super();
	}
	
	@Override
	protected boolean checkElement(Element<?> pelement){
		return true;
	}

	
}
 