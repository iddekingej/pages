package org.elaya.page.form;

import java.util.ListIterator;
import java.util.Set;
import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.ThemeItemBase;
import org.elaya.page.form.FormExceptions.InvalidElement;

public class Form extends PageElement<FormThemeItem>{
	
	public Form() {
		super();
	}

	private String title;
	private String url;
	
	public void addJsFile(Set<String> p_set)
	{
		p_set.add("form.js");
		ListIterator<Element<ThemeItemBase>> l_iter=elements.listIterator();
		while(l_iter.hasNext()){
			l_iter.next().addJsFile(p_set);
		}
		
	}
	
		
	public void checkElement(Element<ThemeItemBase> p_element) throws InvalidElement
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
		ListIterator<Element<ThemeItemBase>> l_iter=elements.listIterator();
		FormElement<ThemeItemBase> l_item;
		Object l_value;
		while(l_iter.hasNext()){
			l_item=(FormElement<ThemeItemBase>)l_iter.next();
			themeItem.formElementBegin(l_item.getLabel());
			if(l_item.hasValue()){
				if(hasValue(l_item.getName())){
					l_value=getValue(l_item.getName());
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
		themeItem.jsBegin();
		themeItem.print("{\n l_temp=[];");
		for(Element l_element:elements){
			if(l_element instanceof FormElement){
				FormElement l_fe=(FormElement)l_element;
				themeItem.print("   l_temp["+themeItem.js_toString(l_fe.getName())+"]="+themeItem.js_toString(l_fe.getJsType())+";\n");
			}
		}
		themeItem.print("}\n");
		themeItem.jsEnd();
	}
	
 
}
