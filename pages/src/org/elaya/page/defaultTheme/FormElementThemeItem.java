package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import javax.servlet.http.HttpServletResponse;
import org.elaya.page.*;

public class FormElementThemeItem extends org.elaya.page.FormElementThemeItem {

	public void textElement(String p_name,String p_value) throws IOException
	{
		print("<input "+property("type","text")+property("name",p_name)+property("value",p_value)+">");
	}
	
	public void radioOption(String p_name,String p_value,String p_text) throws IOException{
			print("<input "+property("type","radio")+property("name",p_name)+property("value",p_value)+">");
			print(escape(p_text));
	}
	
	public void radioElement(String p_name,LinkedList<OptionItem> p_items,boolean p_isHorizontal) throws IOException
	{
		Iterator<OptionItem> l_iter=p_items.iterator();
		OptionItem l_item;
		while(l_iter.hasNext()){
			l_item=l_iter.next();
			radioOption(p_name,l_item.getValue(),l_item.getText());
			if(!p_isHorizontal){
				print("<br/>");
			}
		}
	}
	
	public FormElementThemeItem(HttpServletResponse p_response) throws IOException {
		super(p_response);
	}

}
