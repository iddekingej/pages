package org.elaya.page.widget.element;

import java.io.IOException;

import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.widget.Element;
import org.elaya.page.widget.PageElement;


public class MenuBar extends PageElement<ElementThemeItem> {

	@Override
	protected void preElement(int id,Writer pwriter,Data data,Element<?> pelement) throws IOException 
	{
		themeItem.menuBarItemHeader(pwriter);
	}
	@Override
	protected void postElement(int id,Writer pwriter,Data data,Element<?> pelement) throws IOException
	{		
		themeItem.menuBarItemFooter(pwriter);
	}
	
	@Override
	public void displayElement(int id,Writer pstream, Data pdata) throws org.elaya.page.widget.Element.DisplayException {
		try{
			themeItem.menuBarHeader(pstream);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override
	public void displayElementFooter(int id,Writer pstream, Data pdata) throws org.elaya.page.widget.Element.DisplayException {
		try{
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
