package org.elaya.page;

import java.util.LinkedList;
import java.util.Objects;

public abstract class Element<themeType> {
	protected themeType themeItem;
	protected Theme theme;
	protected LinkedList<Element> elements=new LinkedList<Element>();
	
	
	public abstract void display() throws Exception;
	public abstract void display(String p_string) throws Exception;
	public abstract String getThemeName();
	
	@SuppressWarnings("unchecked")
	final public void setTheme(Theme p_theme) throws Exception
	{
		theme=p_theme;
		themeItem=(themeType)p_theme.getTheme(getThemeName());
		if(themeItem==null){
			throw new Exception("ThemeItem null for :"+getThemeName());
		}
	}
	
	final public void addElement(Element p_element) throws Exception
	{
		Objects.requireNonNull(p_element,"addElement(p_element)");
		p_element.setTheme(theme);
		elements.add(p_element);
	}
	
	final public LinkedList<Element> getElements()
	{
		return elements;
	}
	
}
