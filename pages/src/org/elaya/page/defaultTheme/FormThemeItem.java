package org.elaya.page.defaultTheme;

import java.io.IOException;
import org.elaya.page.Theme;

public class FormThemeItem extends org.elaya.page.form.FormThemeItem {

	public void formHeader(String p_title,String p_url) throws IOException
	{
		print("<form "+property("action",p_url)+"'><table class=\"pages_formTable\">\n");
		print("<tr><td colspan='2' class='pages_formTitle'>"+escape(p_title)+"</td>");
	}
	
	public void formFooter() throws IOException
	{
		print("<tr><td colspan='2'><input type='button' name='submit' value='save'></td></tr>");

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
	
	 
	
	public FormThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

}
