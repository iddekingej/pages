package org.elaya.page;

import java.util.ListIterator;

public class Page extends PageElement<PageThemeItem> {

		
	public void display() throws Exception
	{
		ListIterator<Element> l_iter=getElements().listIterator();
		themeItem.pageHeader();
		while(l_iter.hasNext()){
			l_iter.next().display();
		}
		themeItem.pageFooter();
	}	
	
	public String getThemeName()
	{
			return "PageThemeItem";
	}
	
	public Page(Application p_application) throws Exception
	{
		setTheme(new Theme(p_application));
	}

}



