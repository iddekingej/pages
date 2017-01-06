package org.elaya.page.layout;

import org.elaya.page.PageElement;

public abstract class Layout extends PageElement<LayoutThemeItem> {

	public Layout() {
		super();
		setIsWidgetParent(false);
	}

	
	@Override
	public String getThemeName() {
		return "LayoutThemeItem";
	}

}
