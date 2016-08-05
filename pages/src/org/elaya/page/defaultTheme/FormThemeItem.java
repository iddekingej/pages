package org.elaya.page.defaultTheme;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class FormThemeItem extends org.elaya.page.FormThemeItem {

	public void formHeader(String p_title,String p_url) throws IOException
	{
		print("<form "+property("action",p_url)+"'><table class=\"pages_formTable\">\n");
		print("<tr><td colspan='2' class='pages_formTitle'>"+escape(p_title)+"</td>");
	}
	
	public void formFooter() throws IOException
	{
		print("</table></form>");
	}
	
	public void formElementBegin(String p_label) throws Exception
	{
		print("<tr><td "+property("class","pages_elementLabel")+">"+escape(p_label)+"</td><td class=\"pages_elementValue\">");
	}
	
	public void formElementEnd() throws Exception
	{
		print("</td></tr>\n");
	}
	
	 
	
	public FormThemeItem(HttpServletResponse p_response) throws IOException {
		super(p_response);
	}

}
