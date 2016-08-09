package org.elaya.page.form;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.form.FormExceptions.InvalidElement;

public class Form extends PageElement<FormThemeItem>{
	
	private String title;
	private String url;
	private HashMap<String,String> values=new HashMap<String,String>();
	
	public void setValue(String p_name,String p_value)
	{
		values.put(p_name,p_value);
	}
	
	public void checkElement(Element p_element) throws InvalidElement
	{
		if(!(p_element instanceof FormElement)) throw new FormExceptions.InvalidElement(p_element, this, "org.elaya.form.FormElement"); 
	}
	
	public void setTitle(String p_title)
	{
		title=p_title;
	}
	
	public String getThemeName()
	{	
		return "FormThemeItem";		
	}
	
	public void display() throws Exception
	{
		themeItem.formHeader(title,url);
		ListIterator<Element> l_iter=elements.listIterator();
		FormElement l_item;
		String l_value;
		while(l_iter.hasNext()){
			l_item=(FormElement)l_iter.next();
			themeItem.formElementBegin(l_item.getLabel());
			if(l_item.hasValue()){
				if(values.containsKey(l_item.getName())){
					l_value=values.get(l_item.getName());
				} else {
					l_value="";
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
