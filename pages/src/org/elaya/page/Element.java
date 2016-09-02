package org.elaya.page;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Set;

import org.elaya.page.data.DynamicMethod;

public abstract class Element<themeType extends ThemeItemBase> extends DynamicMethod {
	protected themeType themeItem;
	protected Theme theme;
	protected LinkedList<Element<?>> elements=new LinkedList<Element<?>>();
	private   Element<?> parent=null;
	private static int idCnt=0;
	private int id;
	private String name="";
	private HorizontalAlign horizontalAlign=HorizontalAlign.left;
	private VerticalAlign  verticalAlign=VerticalAlign.top;
	private String layoutWidth;
	private String layoutHeight;
	
	public Element()
	{
		id=idCnt;
		idCnt++;
	}
	
	public void setLayoutWidth(String p_width)
	{
		layoutWidth=p_width;
	}
	
	public String getLayoutWidth()
	{
		return layoutWidth;
	}
	
	
	public void setLayoutHeight(String p_height)
	{
		layoutHeight=p_height;
	}
	
	public String geLayoutHeight()
	{
		return layoutHeight;
	}
	
	public HorizontalAlign getHorizontalAlign()
	{
		return horizontalAlign;
	}
	
	public VerticalAlign getVerticalAlign()
	{
		return verticalAlign;
	}
	
	public void setHorizontalAlign(HorizontalAlign p_align)
	{
		horizontalAlign=p_align;
	}
	
	public void setVerticalalign(VerticalAlign p_align)
	{
		verticalAlign=p_align;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getDomId()
	{
		return "element_"+id;
	}
	

	
	public void setName(String p_name)
	{
		name=p_name;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getJsName()
	{
		if(name.length()==0){
			return "E"+Integer.toString(id);
		}
		return name;
	}

	public String getJsFullname() throws IOException
	{
		if(parent==null) return getJsName();
		String l_parent=parent.getJsFullname();
		if(l_parent.length()==0){
			return getJsName();
		}
		return l_parent+".elements."+getJsName();
	}
	
	public void getAllCssFiles(Set<String> p_files)
	{
		addCssFile(p_files);
		for(Element<?> l_element:elements){
			l_element.getAllCssFiles(p_files);
		}
	}
	
	public void getAllJsFiles(Set<String> p_files)
	{
		addJsFile(p_files);
		for(Element<?> l_element:elements){
			l_element.getAllJsFiles(p_files);
		}		
	}
	
	public void addJsFile(Set<String> p_set)
	{ 
		
	}
	
	public void addCssFile(Set<String> p_files)
	{
			themeItem.getCssFiles(p_files);
	}
	
	public String getObjectName()
	{
		return "object_"+Integer.toString(id);
	}
	public String getVarname()
	{
		return "var_"+Integer.toString(id);
	}
	
	public Element<?> getParent(){ return parent;}
	
	void setParent(Element<?> p_parent) throws IOException{
		parent=p_parent;
	}
	
	public Page getPage()
	{
		Element<?> l_current=getParent();
		while(l_current != null){
			if(l_current instanceof Page){
				return (Page)l_current;
			}
			l_current=l_current.getParent();
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
		ThemeItemBase l_theme=p_theme.getTheme(getThemeName());
		Objects.requireNonNull(l_theme,"themeItem=>setTheme");
		themeItem=(themeType)p_theme.getTheme(getThemeName());
	}
	
	protected void checkSubElement(Element<ThemeItemBase> p_element)
	{
	}
	
	final public void addElement(Element<?> p_element) throws Exception
	{
		Objects.requireNonNull(p_element,"addElement(p_element)");
		p_element.setTheme(theme);
		p_element.setParent(this);
		elements.add(p_element);
		if(p_element.getName().length()>0){
			getPage().addToNameIndex(p_element);
		}

	}
	
	final public LinkedList<Element<?>> getElements()
	{
		return elements;
	} 
	
}
