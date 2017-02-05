package org.elaya.page.widget.element;

import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;

public class SeperatorMenuItem extends BaseMenuItem<ElementThemeItem> {

	@Override
	public void displayElement(int id,Writer pstream, Data pdata) throws org.elaya.page.widget.Element.DisplayException {
//Everything is done in JS
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

	@Override
	public String getJsClassName() {
		return "TSeperatorMenuItem";
	}	
}
