package org.elaya.page;


public abstract class Layout extends PageElement<LayoutThemeItem> {

	public Layout() {
		super();
	}

	@Override
	public Element<?> getFirstWidget()
	{
		return this.getParent().getFirstWidget();
	}
	
	@Override
	public String getThemeName() {
		return "LayoutThemeItem";
	}

}
