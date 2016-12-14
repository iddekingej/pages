package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Menu extends PageElement<ElementThemeItem> {
	private String title;
	
	public String getTitle(){ return title;}
	public void setTitle(String ptitle){ title=ptitle;}
	@Override
	public void display(Writer pstream, Data pdata) throws Exception {
		Data data=getData(pdata);
		themeItem.menu(pstream, getDomId(), replaceVariables(data,title));
		
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
