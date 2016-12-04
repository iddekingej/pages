package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import org.elaya.page.*;
import org.elaya.page.quickform.OptionItem;

public class FormElementThemeItem extends org.elaya.page.quickform.FormElementThemeItem {

		
	public void textElement(Writer p_writer,String p_idDom,String p_name,Object p_value,int p_maxLength) throws IOException
	{
		p_writer.print("<input type='text' style='width:100%' "+property("id",p_idDom)+property("name",p_name)+property("value",p_value)+propertyF("maxlength",(p_maxLength>0?String.valueOf(p_maxLength):""))+"/>");
	}
	
	public void passwordElement(Writer p_writer,String p_idDom,String p_name,Object p_value,int p_maxLength) throws IOException
	{
		p_writer.print("<input type='password' style='width:100%' "+property("id",p_idDom)+property("name",p_name)+property("value",p_value)+propertyF("maxlength",(p_maxLength>0?String.valueOf(p_maxLength):""))+"/>");
	}
	
	
	public void selectElementHeader(Writer p_writer,String p_idDom,String p_name) throws IOException
	{
		p_writer.print("<select  "+property("id",p_idDom)+property("name",p_name)+">");
	}
	
	public void selectElementOption(Writer p_writer,Object p_value,String p_text,boolean p_selected) throws IOException
	{
		p_writer.print("<option "+property("value",p_value)+(p_selected?"selected='1'":"")+">"+escape(p_text)+"</option>");
	}
	
	public void selectElementFooter(Writer p_writer) throws IOException
	{
		p_writer.print("</select>");
	}
	public void selectElement(Writer p_writer,String p_idDom,String p_name,LinkedList<OptionItem> p_items,Object p_value) throws IOException
	{		
		selectElementHeader(p_writer,p_idDom,p_name);
		for(OptionItem l_item:p_items){
			selectElementOption(p_writer,l_item.getValue(),l_item.getText(),l_item.getValue().equals(p_value));
		}
		selectElementFooter(p_writer);
	}

	
	public void radioOption(Writer p_writer,String p_idDom,String p_name,Object p_value,String p_text,boolean p_selected) throws IOException{
		p_writer.print("<input "+property("id",p_idDom)+property("type","radio")+property("name",p_name)+property("value",p_value)+(p_selected?"checked='1'":"")+"/>");
		p_writer.print("<label "+property("for",p_idDom)+">"+escape(p_text)+"</label>");
	}
	
	
	public void radioElement(Writer p_writer,String p_idDom,String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal,Object p_value) throws IOException
	{
		Iterator<OptionItem> l_iter=p_items.iterator();
		OptionItem l_item;
		int l_cnt=0;
		while(l_iter.hasNext()){
			l_item=l_iter.next();
			radioOption(p_writer,p_idDom+"_"+l_cnt,p_name,l_item.getValue(),l_item.getText(),l_item.getValue().equals(p_value));
			if(!p_isHorizontal){
				p_writer.print("<br/>");
			}
			l_cnt++;
		}		
	}
	
	public void checkBoxElement(Writer p_writer,String p_idDom,String p_name,Object p_value) throws IOException
	{
		p_writer.print("<input "+property("id",p_idDom)+property("name",p_name)+property("type","checkbox")+((p_value.toString().length()>0)?"checked='1'":"")+"/>");
	}
	
	public void StaticElement(Writer p_writer,String p_idDom,String p_name,boolean p_isHtml,Object p_value) throws IOException
	{
		p_writer.print("<div "+property("id",p_idDom)+">");
		if(p_isHtml){
			p_writer.print(p_value.toString());
		} else {
			p_writer.print(escape(p_value.toString()));
		}
		p_writer.print("</div>");
		
	}

	@Override
	public void textArea(Writer p_writer,String p_idDom, String p_name, String p_height, String p_width, Object p_value)
			throws IOException {
		String l_css="";
		if(p_width.length()>0) l_css += "width:"+p_width+";";
		if(p_height.length()>0) l_css += "height:"+p_height+";";
		p_writer.print("<textarea "+property("id",p_idDom)+property("name",p_name)+property("style",l_css)+">"+escape(p_value)+"</textarea>");
	}
	
	@Override
	public void dateElement(Writer p_writer,String p_domId,String p_name,Object p_value) throws IOException
	{
		p_writer.print("<input type='text' "+property("id",p_domId)+property("value",str(p_value))+"/>");
	}

	@Override
	public void elementBegin(String p_domId,Writer p_writer,String p_label) throws Exception
	{
		p_writer.print("<tr "+property("id",p_domId)+"><td "+property("class","pages_elementLabel")+">"+escape(p_label)+"</td><td class=\"pages_elementValue\">");
		
	}

	@Override
	public void elementBeginTop(String p_domId,Writer p_writer,String p_label) throws Exception
	{
		p_writer.print("<tr "+property("id",p_domId)+"><td "+property("class","pages_elementLabel")+">"+escape(p_label)+"</td colspan='2'></tr><tr><td class=\"pages_elementValue\" colspan='2'>");
	}
	
	@Override
	public void elementEnd(Writer p_writer) throws Exception
	{
		p_writer.print("</td></tr>\n");
	}
	
}
