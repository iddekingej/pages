package org.elaya.page;


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
