package org.elaya.page.element;

import org.elaya.page.PageElement;

public class VerticalSpacer extends PageElement<ElementThemeItem> {

	public VerticalSpacer() {
		super();
		setLayoutWidth("100%");
	}

	@Override
	public void display() throws Exception {
		themeItem.verticalSpacer();
	}

	@Override
	public String getThemeName() {
		// TODO Auto-generated method stub
		return "ElementThemeItem";
	}

}
