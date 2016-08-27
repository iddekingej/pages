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
	private String cmd;
	
	public void setCmd(String p_cmd)
	{
		cmd=p_cmd;
	}
	
	public String getCmd(){ return cmd;}
	
	public void addJsFile(Set<String> p_set)
	{
		p_set.add("form.js");
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
		themeItem.formHeader(getDomId(),title,url);
		
		FormElement<ThemeItemBase> l_item;
		Object l_value;		
		for(Element<ThemeItemBase> l_element:elements){
			if(l_element instanceof FormElement){
				l_item=(FormElement<ThemeItemBase>)l_element;
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
		}

		themeItem.formFooter(getDomId());
		themeItem.jsBegin();
		themeItem.print("\n{\n ");
		themeItem.print("var l_form=new TForm("+getParent().getJsFullname()+","+themeItem.js_toString(getJsName())+","+themeItem.js_toString(getName())+","+themeItem.js_toString(getDomId())+");\n");
		themeItem.print("l_form.cmd="+themeItem.js_toString(cmd)+";\n");
		for(Element<ThemeItemBase> l_element:elements){
			if(l_element instanceof FormElement){
				FormElement<ThemeItemBase> l_fe=(FormElement<ThemeItemBase>)l_element;
				themeItem.print(l_fe.getObjectJs("l_form")+"\n");
			}
		}
		themeItem.print("\n}\n");
		themeItem.jsEnd();
	}
	
 
}
