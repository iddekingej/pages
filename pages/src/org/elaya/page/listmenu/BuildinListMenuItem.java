package org.elaya.page.listmenu;

public abstract class BuildinListMenuItem extends ListMenuItem<ListMenuThemeItem> {

	public BuildinListMenuItem()
	{
		super();
	}

	
	@Override
	final public String getThemeName() {
		return "ListMenuThemeItem";
	}

}
