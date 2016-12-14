package org.elaya.page.element;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class SeperatorMenuItem extends BaseMenuItem<ElementThemeItem> {

	@Override
	public void display(Writer pstream, Data pdata) throws Exception {
		themeItem.menuSeperator(pstream);
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
