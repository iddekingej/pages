package org.elaya.page;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

public class Form extends Element<FormThemeItem>{
	

	
	
	private String title;
	private String url;
	private LinkedList<FormElement> elements=new LinkedList<FormElement>();
	private HashMap<String,String> values=new HashMap<String,String>();
	
	public void setValue(String p_name,String p_value)
	{
		values.put(p_name,p_value);
	}
	
	public void setTitle(String p_title)
	{
		title=p_title;
	}
	
	public void addElement(FormElement p_element) throws Exception
	{
		p_element.setTheme(theme);
		elements.add(p_element);
	}
	
	public String getThemeName()
	{	
		return "FormThemeItem";		
	}
	
	public void display() throws Exception
	{
		themeItem.formHeader(title,url);
		ListIterator<FormElement> l_iter=elements.listIterator();
		FormElement l_item;
		String l_value;
		while(l_iter.hasNext()){
			l_item=l_iter.next();
			themeItem.formElementBegin(l_item.getLabel());
			if(l_item.hasValue()){
				if(values.containsKey(l_item.getName())){
					l_value=values.get(l_item.getName());
				} else {
					throw new FormExceptions.ValueNotFoundException(l_item.getName());
				}
			} else {
				l_value="";
			}
			l_item.display(l_value);			
			themeItem.formElementEnd();
		}
		themeItem.formFooter();
	}
	

}
