package org.elaya.page.element;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class MenuBar2 extends PageElement<ElementThemeItem> {

	public MenuBar2() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void preElement(Writer p_writer,Element<?> p_element) throws IOException, Exception
	{
		themeItem.menuBarItemHeader(p_writer);
	}
	@Override
	protected void postElement(Writer p_writer,Element<?> p_element) throws IOException, Exception
	{		
		themeItem.menuBarItemFooter(p_writer);
	}
	
	@Override
	public void display(Writer p_stream, Data p_data) throws Exception {
		themeItem.menuBarHeader(p_stream);
		displaySubElements(p_stream,p_data);
		themeItem.menuBarFooter(p_stream);
		
	}
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
