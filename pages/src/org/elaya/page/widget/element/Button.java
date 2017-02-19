package org.elaya.page.widget.element;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.PageElement;

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
	public void displayElement(int id, Writer stream, Data data) throws org.elaya.page.widget.Element.DisplayException {
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
