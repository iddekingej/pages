package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;

public class HorizontalSpacer extends PageElement<ElementThemeItem> {

	public HorizontalSpacer() {
		super();
		setLayoutWidth("100%");
	}

	@Override
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException{
		try{
			themeItem.horizontalSpacer(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
