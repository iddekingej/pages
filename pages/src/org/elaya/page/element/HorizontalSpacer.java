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
	public void display(Writer p_writer,Data p_data) throws Exception {
		themeItem.horizontalSpacer(p_writer);
	}

	@Override
	public String getThemeName() {
		// TODO Auto-generated method stub
		return "ElementThemeItem";
	}

}
