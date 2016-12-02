package org.elaya.page.element;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class SeperatorMenuItem extends BaseMenuItem<ElementThemeItem> {

	public SeperatorMenuItem() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void display(Writer p_stream, Data p_data) throws Exception {
		themeItem.menuSeperator(p_stream);
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
