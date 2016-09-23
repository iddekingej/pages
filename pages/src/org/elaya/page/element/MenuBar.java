package org.elaya.page.element;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class MenuBar extends PageElement<ElementThemeItem> {
	public boolean checkElement(Element<?> p_element){
		return p_element instanceof SubMenu;
	}
	
	protected String getJsClassName()
	{
		return "TMenuBar";
	}
	
	@Override
	public void display(Writer p_writer, Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.MenubarHeader(p_writer, getDomId());
		displaySubElements(p_writer,l_data);
		themeItem.MenubarFooter(p_writer);
	}
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
