package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.Theme;

public class FormThemeItem extends org.elaya.page.form.FormThemeItem {

	public void getCssFiles(Set<String> p_files){ 
		p_files.add("form.css");
	}
	public void formHeader(String p_domId,String p_title,String p_url) throws IOException
	{
		print("<form "+property("id",p_domId)+property("action",p_url)+"><table class=\"pages_formTable\">\n");
		print("<tr><td colspan='2' class='pages_formTitle'>"+escape(p_title)+"</td>");
	}
	
	public void formFooter(String p_domId) throws IOException
	{
		print("<tr><td colspan='2'><input "+property("id",p_domId+"_submit")+"type='button' name='submit' value='save'></td></tr>");

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
