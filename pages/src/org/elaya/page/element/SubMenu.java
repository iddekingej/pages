package org.elaya.page.element;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class SubMenu extends PageElement<ElementThemeItem> {
	private String icon="";
	private String title="";
	
	public void setIcon(String p_icon)
	{
		icon=p_icon;
	}
	
	public String getIcon()
	{
		return icon;
	}
	
	public void setTitle(String p_title)
	{
		title=p_title;
	}
	
	public String getTitle()
	{
		return title;
	}

	
	public boolean checkElement(Element<?> p_element){
		return p_element instanceof MenuItem;
	}
	@Override
	public void display(Writer p_writer, Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.SubMenuHeader(p_writer,replaceVariables(l_data,icon), replaceVariables(l_data,title));
		displaySubElements(p_writer,l_data);
		themeItem.SubMenuFooter(p_writer);
	}

	@Override
	public String getThemeName() {
		// TODO Auto-generated method stub
		return "ElementThemeItem";
	}

}
