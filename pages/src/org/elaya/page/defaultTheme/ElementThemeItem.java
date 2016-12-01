package org.elaya.page.defaultTheme;

import java.io.IOException;

import org.elaya.page.Theme;
import org.elaya.page.Writer;

public class ElementThemeItem extends org.elaya.page.element.ElementThemeItem {

	public ElementThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	@Override
	public void staticElement(Writer p_writer,String p_text, boolean p_isHtml, String p_class, String p_css) throws IOException {
		p_writer.print("<span "+propertyF("class",p_class)+propertyF("style",p_css)+">");
		if(p_isHtml){
			p_writer.print(p_text);
		} else {
			p_writer.print(escape(p_text));
		}
		p_writer.print("</span>");

	}

	@Override
	public void image(Writer p_writer,String p_url, String p_class, String p_css) throws IOException {
		p_writer.print("<img "+property("src",p_url)+propertyF("class",p_class)+propertyF("style",p_css)+"/>");

	}

	@Override
	public void panelHeader(Writer p_writer,String p_class, String p_css) throws IOException {
		p_writer.print("<div "+propertyF("class",p_class)+propertyF("style",p_css)+">");

	}

	@Override
	public void panelFooter(Writer p_writer) throws IOException {
		p_writer.print("</div>");
	}

	@Override
	public void link(Writer p_writer,String p_url, String p_text, String p_class, String p_css) throws IOException {
		p_writer.print("<a "+property("href",p_url)+propertyF("class",p_class)+propertyF("style",p_css)+">"+escape(p_text)+"</a>");
	}

	@Override
	public void horizontalSpacer(Writer p_writer) throws IOException {
		p_writer.print("<div style='width:100%'>&#160;</div>");
	}

	@Override
	public void verticalSpacer(Writer p_writer) throws IOException {
		p_writer.print("<div style='height:100%'>&#160;</div>");
	}
	
	@Override
	public void MenubarHeader(Writer p_writer,String p_domId) throws IOException
	{
		p_writer.print("<ul "+property("id",p_domId)+">\n");
	}
	@Override
	public void MenubarFooter(Writer p_writer) throws IOException{
		p_writer.print("</ul>\n");
	}
	@Override
	public void SubMenuHeader(Writer p_writer,String p_icon,String p_title)  throws IOException
	{
	    p_writer.print("<li> <a "+propertyF("data-icon",p_icon)+">"+escape(p_title)+"</a><ul>\n");
	}
	@Override
	public void SubMenuFooter(Writer p_writer)  throws IOException
	{
		p_writer.print("</ul></li>\n");
	}
	
	@Override
	public void MenuItem(Writer p_writer,String p_icon,String p_title,String p_url)  throws IOException
	{
		  p_writer.print("<li> <a "+propertyF("data-icon",p_icon)+propertyF("href",p_url)+">"+escape(p_title)+"</a></li>\n");
	}

	@Override
	public void menuBarHeader(Writer p_writer) throws IOException {
		p_writer.print("<table class='menubar'><tr>");
		
	}

	@Override
	public void menuBarItemHeader(Writer p_writer) throws IOException {
		p_writer.print("<td class='menubar_item'>");
	}

	@Override
	public void menuBarItemFooter(Writer p_writer) throws IOException {
		p_writer.print("</td>");
		
	}

	@Override
	public void menuBarFooter(Writer p_writer) throws IOException {
		p_writer.print("<td style='width:100%'>&nbsp;</td></tr></table>");
	}
	


	@Override
	public void menu(Writer p_writer, String p_id, String p_title) throws IOException {
		// TODO Auto-generated method stub
		p_writer.print("<div "+property("id",p_id)+" >"+escape(p_title)+"<div "+property("id",p_id+"_menu")+"style='position:absolute;left:0px;top:0px;height:100px;width:500px;color:#F00;display:none'></div>"+"</div>");
	}

	
}
