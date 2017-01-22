package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Button extends PageElement<ElementThemeItem> {
	private String text;
	
	public void setText(String ptext){
		text=ptext;
	}
	
	public String getText()
	{
		return text;
	}
	
	@Override
	public void displayElement(int id, Writer stream, Data data) throws org.elaya.page.Element.DisplayException {
		try{
			themeItem.button(stream, getDomId(id),text);
		}catch(Exception e)
		{
			throw new DisplayException(e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
