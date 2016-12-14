package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class HorizontalSpacer extends PageElement<ElementThemeItem> {

	public HorizontalSpacer() {
		super();
		setLayoutWidth("100%");
	}

	@Override
	public void display(Writer pwriter,Data pdata) throws Exception {
		themeItem.horizontalSpacer(pwriter);
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
