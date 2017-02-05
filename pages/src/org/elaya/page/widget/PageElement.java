package org.elaya.page.widget;

import org.elaya.page.ThemeItemBase;

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
 