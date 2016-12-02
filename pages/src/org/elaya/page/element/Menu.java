package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Menu extends PageElement<ElementThemeItem> {
	private String title;
	
	public String getTitle(){ return title;}
	public void setTitle(String p_title){ title=p_title;}
	@Override
	public void display(Writer p_stream, Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.menu(p_stream, getDomId(), replaceVariables(l_data,title));
		
	}

	@Override
	public String getJsClassName() {
		return "TMenu";
	}	
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
