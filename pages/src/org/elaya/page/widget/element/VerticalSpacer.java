package org.elaya.page.widget.element;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.PageElement;

public class VerticalSpacer extends PageElement<ElementThemeItem> {

	public VerticalSpacer() {
		super();
		setLayoutHeight("100%");
	}

	@Override
	public void displayElement(int id,Writer pwriter, Data pdata) throws org.elaya.page.widget.Element.DisplayException{
		try{
			themeItem.verticalSpacer(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
