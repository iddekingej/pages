package org.elaya.page;


public abstract class Layout extends PageElement<LayoutThemeItem> {

	public Layout() {
		super();
	}

	public Element<?> getFirstWidget()
	{
		return this.getParent().getFirstWidget();
	}
	
	public String getThemeName() {
		return "LayoutThemeItem";
	}

}
