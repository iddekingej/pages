package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.elaya.page.*;
import org.elaya.page.quickform.OptionItem;

public class FormElementThemeItem extends org.elaya.page.quickform.FormElementThemeItem {

		
	public void textElement(String p_idDom,String p_name,Object p_value) throws IOException
	{
		print("<input "+property("id",p_idDom)+property("type","text")+property("name",p_name)+property("value",p_value)+">");
	}
	

	public void selectElementHeader(String p_idDom,String p_name) throws IOException
	{
		print("<select "+property("id",p_idDom)+property("name",p_name)+">");
	}
	
	public void selectElementOption(Object p_value,String p_text,boolean p_selected) throws IOException
	{
		print("<option "+property("value",p_value)+(p_selected?"selected='1'":"")+">"+escape(p_text)+"</option>");
	}
	
	public void selectElementFooter() throws IOException
	{
		print("</select>");
	}
	public void selectElement(String p_idDom,String p_name,LinkedList<OptionItem> p_items,Object p_value) throws IOException
	{		
		selectElementHeader(p_idDom,p_name);
		Iterator<OptionItem> l_iter=p_items.iterator();
		OptionItem l_item;
		while(l_iter.hasNext()){
			l_item=l_iter.next();
			selectElementOption(l_item.getValue(),l_item.getText(),l_item.getValue().equals(p_value));
		}
		selectElementFooter();
	}

	
	public void radioOption(String p_idDom,String p_name,Object p_value,String p_text,boolean p_selected) throws IOException{
		print("<input "+property("id",p_idDom)+property("type","radio")+property("name",p_name)+property("value",p_value)+(p_selected?"checked='1'":"")+">");
		print(escape(p_text));
	}
	
	
	public void radioElement(String p_idDom,String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal,Object p_value) throws IOException
	{
		Iterator<OptionItem> l_iter=p_items.iterator();
		OptionItem l_item;
		int l_cnt=0;
		while(l_iter.hasNext()){
			l_item=l_iter.next();
			radioOption(p_idDom+"_"+l_cnt,p_name,l_item.getValue(),l_item.getText(),l_item.getValue().equals(p_value));
			if(!p_isHorizontal){
				print("<br/>");
			}
			l_cnt++;
		}		
	}
	
	public void checkBoxElement(String p_idDom,String p_name,Object p_value) throws IOException
	{
		print("<input "+property("id",p_idDom)+property("name",p_name)+property("type","checkbox")+((p_value.toString().length()>0)?"checked='1'":"")+">");
	}
	
	public void StaticElement(String p_idDom,String p_name,boolean p_isHtml,Object p_value) throws IOException
	{
		print("<div "+property("id",p_idDom)+">");
		if(p_isHtml){
			print(p_value.toString());
		} else {
			print(escape(p_value.toString()));
		}
		print("</div>");
		
	}

	
	public FormElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

}
