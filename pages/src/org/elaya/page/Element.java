package org.elaya.page;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

public abstract class Element<themeType> {
	protected themeType themeItem;
	protected Theme theme;
	protected LinkedList<Element<ThemeItemBase>> elements=new LinkedList<Element<ThemeItemBase>>();
	private   Element<ThemeItemBase> parent=null;
	private static int idCnt=0;
	private int id;
	
	public Element()
	{
		id=idCnt;
		idCnt++;
	}
	
	public void addJsFile(Set<String> p_set)
	{
		
	}
	
	public String getObjectName()
	{
		return "object_"+Integer.toString(id);
	}
	public String getVarname()
	{
		return "var_"+Integer.toString(id);
	}
	
	public Element getParent(){ return parent;}
	void setParent(Element p_parent){
		parent=p_parent;
	}
	
	public Page getPage()
	{
		Element l_current=getParent();
		while(l_current != null && !(l_current instanceof Page)) l_current=l_current.getParent();
		if(l_current !=null){
			return (Page)l_current;
		}
		return null;
	}
	
	public abstract void display() throws Exception;
	public abstract void display(Object p_string) throws Exception;
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
	
	protected void checkSubElement(Element<ThemeItemBase> p_element)
	{
	}
	
	final public void addElement(Element<ThemeItemBase> p_element) throws Exception
	{
		Objects.requireNonNull(p_element,"addElement(p_element)");
		p_element.setTheme(theme);
		p_element.setParent(this);
		elements.add(p_element);
	}
	
	final public LinkedList<Element<ThemeItemBase>> getElements()
	{
		return elements;
	}
	
}
