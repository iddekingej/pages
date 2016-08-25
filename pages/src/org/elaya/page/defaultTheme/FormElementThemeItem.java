package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.elaya.page.*;
import org.elaya.page.form.OptionItem;

public class FormElementThemeItem extends org.elaya.page.form.FormElementThemeItem {

		
	public void textElement(String p_name,Object p_value) throws IOException
	{
		print("<input "+property("type","text")+property("name",p_name)+property("value",p_value)+">");
	}
	
	public void radioOption(String p_name,Object p_value,String p_text,boolean p_selected) throws IOException{
		print("<input "+property("type","radio")+property("name",p_name)+property("value",p_value)+(p_selected?"checked='1'":"")+">");
		print(escape(p_text));
	}
	
	public void selectElementHeader(String p_name) throws IOException
	{
		print("<select "+property("name",p_name)+">");
	}
	
	public void selectElementOption(Object p_value,String p_text,boolean p_selected) throws IOException
	{
		print("<option "+property("value",p_value)+(p_selected?"selected='1'":"")+">"+escape(p_text)+"</option>");
	}
	
	public void selectElementFooter() throws IOException
	{
		print("</select>");
	}
	public void selectElement(String p_name,LinkedList<OptionItem> p_items,Object p_value) throws IOException
	{		
		selectElementHeader(p_name);
		Iterator<OptionItem> l_iter=p_items.iterator();
		OptionItem l_item;
		while(l_iter.hasNext()){
			l_item=l_iter.next();
			selectElementOption(l_item.getValue(),l_item.getText(),l_item.getValue().equals(p_value));
		}
		selectElementFooter();
	}

	
	public void radioElement(String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal,Object p_value) throws IOException
	{
		Iterator<OptionItem> l_iter=p_items.iterator();
		OptionItem l_item;
		while(l_iter.hasNext()){
			l_item=l_iter.next();
			radioOption(p_name,l_item.getValue(),l_item.getText(),l_item.getValue().equals(p_value));
			if(!p_isHorizontal){
				print("<br/>");
			}
		}		
	}
	
	public void checkBoxElement(String p_name,Object p_value) throws IOException
	{
		print("<input "+property("name",p_name)+property("type","checkbox")+((p_value.toString().length()>0)?"checked='1'":"")+">");
	}
	
	public void StaticElement(String p_name,boolean p_isHtml,Object p_value) throws IOException
	{
		if(p_isHtml){
			print(p_value.toString());
		} else {
			print(escape(p_value.toString()));
		}
		
	}

	
	public FormElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

}
