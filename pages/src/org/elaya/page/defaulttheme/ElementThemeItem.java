package org.elaya.page.defaulttheme;

import java.io.IOException;

import org.elaya.page.core.Writer;

public class ElementThemeItem extends org.elaya.page.widget.element.ElementThemeItem {

	@Override
	public void staticElement(Writer pwriter,String ptext, boolean pisHtml, String pclass, String pcss) throws IOException {
		pwriter.print("<span "+propertyF("class",pclass)+propertyF("style",pcss)+">");
		if(pisHtml){
			pwriter.print(ptext); 
		} else {
			pwriter.print(escape(ptext));
		}
		pwriter.print("</span>");

	}

	@Override
	public void image(Writer pwriter,String purl, String pclass, String pcss) throws IOException {
		pwriter.print("<img "+property("src",purl)+propertyF("class",pclass)+propertyF("style",pcss)+"/>");

	}

	@Override
	public void panelHeader(Writer pwriter,String pcss) throws IOException {
		pwriter.print("<div "+propertyF("style",pcss)+">");

	}

	@Override
	public void panelFooter(Writer pwriter) throws IOException {
		pwriter.print("</div>");
	}

	@Override
	public void link(Writer pwriter,String purl, String ptext, String pclass, String pcss) throws IOException {
		pwriter.print("<a "+property("href",purl)+propertyF("class",pclass)+propertyF("style",pcss)+">"+escape(ptext)+"</a>");
	}

	@Override
	public void horizontalSpacer(Writer pwriter) throws IOException {
		pwriter.print("<div style='width:100%'>&#160;</div>");
	}

	@Override
	public void verticalSpacer(Writer pwriter) throws IOException {
		pwriter.print("<div style='height:100%'>&#160;</div>");
	}
	
	@Override
	public void menuBarHeader(Writer pwriter) throws IOException {
		pwriter.print("<table class='menubar'><tr>");
		
	}

	@Override
	public void menuBarItemHeader(Writer pwriter) throws IOException {
		pwriter.print("<td class='menubar_item'>");
	}

	@Override
	public void menuBarItemFooter(Writer pwriter) throws IOException {
		pwriter.print("</td>");
		
	}

	@Override
	public void menuBarFooter(Writer pwriter) throws IOException {
		pwriter.print("<td style='width:100%'>&nbsp;</td></tr></table>");
	}
	
	@Override
	public void menu(Writer pwriter, String pid, String ptitle) throws IOException {
		pwriter.print("<div class='menu' "+property("id",pid)+" >"+escape(ptitle)+"</div>");
	}
	@Override
	public void button(Writer writer,String id,String text) throws IOException
	{
		writer.print("\n<input type='button' "+classProperty("button")+property("id",id)+property("value",text)+"/>\n");
	}

	@Override
	public void olmapdiv(Writer pwriter, String pid,int pdivWidth,int pdivHeight) throws IOException {
		pwriter.print("<div "+classProperty("olmap")+property("id",pid)+"style='width:"+escape(pdivWidth)+"px;height:"+escape(pdivHeight)+"px'"+"></div>");
	}

}
