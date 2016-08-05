package org.elaya.page;


import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.servlet.http.HttpServletResponse;

public class Page {
	private Theme theme;
	private PageThemeItem themeItem;
	private LinkedList<Element> elements;;
	
	public void addElement(Element p_element) throws Exception
	{
		elements.add(p_element);
		p_element.setTheme(theme);
	}
	
	public void display() throws Exception
	{
		ListIterator<Element> l_iter=elements.listIterator();
		themeItem.pageHeader();
		while(l_iter.hasNext()){
			l_iter.next().display();
		}
		themeItem.pageFooter();
	}

	private void init() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException
	{
		themeItem=(PageThemeItem)theme.getTheme("PageThemeItem");
		elements=new LinkedList<Element>();
	}
	
	public Page(HttpServletResponse p_response) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		theme=new Theme("org.elaya.page.defaultTheme",p_response);	
		init();
	}
	
	public Page(String p_themeBase,HttpServletResponse p_response) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		theme=new Theme(p_themeBase,p_response);		
		init();
	}
}
