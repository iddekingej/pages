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
	public void display(Writer pwriter, Data pdata) throws org.elaya.page.Element.DisplayException{
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
