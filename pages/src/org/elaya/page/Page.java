package org.elaya.page;

import java.util.HashMap;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import org.elaya.page.form.FormElement;

public class Page extends PageElement<PageThemeItem> {

	String url;
	
	public Page()
	{
		super();
	}
	
	void setUrl(String p_url)
	{		
		url=p_url;
	}

	@SuppressWarnings("unchecked")
	public void display() throws Exception
	{
		ListIterator<Element<ThemeItemBase>> l_iter=getElements().listIterator();
		Element<ThemeItemBase> l_element;
		Set<String> l_js=new HashSet<String>();
		while(l_iter.hasNext()){
			l_iter.next().addJsFile(l_js);
		}
		themeItem.pageHeader(l_js);
		Map<String,Object> l_data=getData();
		l_iter=getElements().listIterator();
		while(l_iter.hasNext()){
			l_element = l_iter.next();
			if(l_element instanceof PageElement){
				((PageElement)l_element).setData(l_data);
			}
			l_element.display();
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



