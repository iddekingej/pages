package org.elaya.page.widget.layout;

import org.elaya.page.widget.PageElement;

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
