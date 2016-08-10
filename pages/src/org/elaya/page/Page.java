package org.elaya.page;

import java.util.HashMap;
import java.util.ListIterator;

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
	
	private HashMap<String,String> values=new HashMap<String,String>();
	
	public void setValue(String p_name,String p_value){
		values.put(p_name, p_value);
	}
	
	public void setValues(HashMap<String,String> p_values ){
		values.putAll(p_values);
	}
	
	public String getValue(String p_name){
		return values.get(p_name);
	}
	
	public boolean hasValues(String p_name){
		return values.containsKey(p_name);
	}
	
	@SuppressWarnings("unchecked")
	public void display() throws Exception
	{
		ListIterator<Element> l_iter=getElements().listIterator();
		Element l_element;
		themeItem.pageHeader();
		while(l_iter.hasNext()){
			l_element = l_iter.next();
			if(l_element instanceof PageElement){
				((PageElement)l_element).setValues(values);
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

	@Override
	public boolean hasValue(String p_name) {
		// TODO Auto-generated method stub
		return false;
	}

}



