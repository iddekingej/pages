package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class VerticalSpacer extends PageElement<ElementThemeItem> {

	public VerticalSpacer() {
		super();
		setLayoutHeight("100%");
	}

	@Override
	public void display(Writer pwriter, Data pdata) throws Exception {
		themeItem.verticalSpacer(pwriter);
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
