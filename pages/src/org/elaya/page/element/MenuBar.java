package org.elaya.page.element;

import java.io.IOException;
import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;


public class MenuBar extends PageElement<ElementThemeItem> {

	
	@Override
	protected void preElement(Writer pwriter,Element<?> pelement) throws IOException 
	{
		themeItem.menuBarItemHeader(pwriter);
	}
	@Override
	protected void postElement(Writer pwriter,Element<?> pelement) throws IOException
	{		
		themeItem.menuBarItemFooter(pwriter);
	}
	
	@Override
	public void display(Writer pstream, Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			Data data=getData(pdata);
			themeItem.menuBarHeader(pstream);
			displaySubElements(pstream,data);
			themeItem.menuBarFooter(pstream);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
