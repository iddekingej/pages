package org.elaya.page.defaulttheme;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.core.Writer;

public class FormThemeItem extends org.elaya.page.widget.quickform.FormThemeItem {

	@Override
	public void getCssFiles(Set<String> pfiles){ 
		pfiles.add("form.css");
	}
	
	@Override
	public void formHiddenElement(Writer pwriter,String pdomId,String pname,String pvalue) throws IOException
	{
		pwriter.print("<input type='hidden' "+property("id",pdomId)+property("name",pname)+property("value",pvalue)+"/>");
	}
	
	@Override
	public void formHeader(Writer pwriter,String pdomId,String ptitle,String purl,String pmethod,String pwidth) throws IOException
	{
		pwriter.print("<form "+property("id",pdomId)+property("method",pmethod)+propertyF("action",purl)+"><table class=\"pages_formTable\">\n");
		pwriter.print("<tr><td colspan='2' class='pages_formTitle' style='width:"+pwidth+";\'>"+escape(ptitle)+"</td></tr>");
	}
	
	@Override
	public void formFooterBegin(Writer pwriter) throws IOException{
		pwriter.print("<tr><td>");
	}
	
	@Override
	public void formFooterOk(Writer pwriter,String pdomId,String psaveText) throws IOException
	{
		pwriter.print("<input "+property("id",pdomId+"_submit")+property("onclick","this.form._control.save()")+"type='button' "+property("value",psaveText)+"/>");
	}

	@Override
	public void formFooterBetween(Writer pwriter) throws IOException
	{
		pwriter.print("</td><td>");
	}
	
	@Override
	public void formFooterCancel(Writer pwriter,String pdomId, String pcancelText) throws IOException
	{
		pwriter.print("<input "+property("id",pdomId+"_cancel")+property("onclick","this.form._control.cancel()")+"type='button' "+property("value",pcancelText)+"/>");
	}
	
	@Override
	public void formFooter(Writer pwriter) throws IOException
	{		
		pwriter.print("</td></tr></table></form>");
	}
}
