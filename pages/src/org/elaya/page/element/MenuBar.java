package org.elaya.page.element;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class MenuBar extends PageElement<ElementThemeItem> {

	
	@Override
	protected void preElement(Writer pwriter,Element<?> pelement) throws Exception
	{
		themeItem.menuBarItemHeader(pwriter);
	}
	@Override
	protected void postElement(Writer pwriter,Element<?> pelement) throws Exception
	{		
		themeItem.menuBarItemFooter(pwriter);
	}
	
	@Override
	public void display(Writer pstream, Data pdata) throws Exception {
		Data data=getData(pdata);
		themeItem.menuBarHeader(pstream);
		displaySubElements(pstream,data);
		themeItem.menuBarFooter(pstream);
		
	}
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
