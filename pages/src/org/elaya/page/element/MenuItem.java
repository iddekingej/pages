package org.elaya.page.element;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class MenuItem extends PageElement<ElementThemeItem> {
	private String icon="";
	private String title="";
	private String url="";
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

	public void setUrl(String p_url)
	{
		url=p_url;
	}
	
	public String getUrl()
	{
		return url;
	}
	public MenuItem() {
		super();
	}

	public boolean checkElement(Element<?> p_element){
		return false;
	}
	
	@Override
	public void display(Writer p_writer, Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.MenuItem(p_writer,replaceVariables(l_data,icon), replaceVariables(l_data,title),getApplication().procesUrl(url));
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
