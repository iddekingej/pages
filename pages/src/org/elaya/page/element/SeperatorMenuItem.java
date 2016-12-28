package org.elaya.page.element;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class SeperatorMenuItem extends BaseMenuItem<ElementThemeItem> {

	@Override
	public void displayElement(int id,Writer pstream, Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			themeItem.menuSeperator(pstream);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
