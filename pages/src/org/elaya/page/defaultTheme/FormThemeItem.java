package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.*;

public class FormThemeItem extends org.elaya.page.quickform.FormThemeItem {

	@Override
	public void getCssFiles(Set<String> p_files){ 
		p_files.add("form.css");
	}
	
	@Override
	public void formHeader(Writer p_writer,String p_domId,String p_title,String p_url,String p_method,String p_width) throws IOException
	{
		p_writer.print("<form "+property("id",p_domId)+property("method",p_method)+propertyF("action",p_url)+"><table class=\"pages_formTable\">\n");
		p_writer.print("<tr><td colspan='2' class='pages_formTitle' style='width:"+p_width+";\'>"+escape(p_title)+"</td></tr>");
	}
	
	@Override
	public void formFooterBegin(Writer p_writer) throws IOException{
		p_writer.print("<tr><td>");
	}
	
	@Override
	public void FormFooterOk(Writer p_writer,String p_domId,String p_saveText) throws IOException
	{
		p_writer.print("<input "+property("id",p_domId+"_submit")+property("onclick","this.form._control.save()")+"type='button' "+property("value",p_saveText)+"/>");
	}

	@Override
	public void FormFooterBetween(Writer p_writer) throws IOException
	{
		p_writer.print("</td><td>");
	}
	
	@Override
	public void FormFooterCancel(Writer p_writer,String p_domId, String p_cancelText) throws IOException
	{
		p_writer.print("<input "+property("id",p_domId+"_cancel")+property("onclick","this.form._control.cancel()")+"type='button' "+property("value",p_cancelText)+"/>");
	}
	
	@Override
	public void formFooter(Writer p_writer) throws IOException
	{		
		p_writer.print("</tr></table></form>");
	}
	
	
	public FormThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}



	

}
