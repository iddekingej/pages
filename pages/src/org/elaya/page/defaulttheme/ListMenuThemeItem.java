package org.elaya.page.defaulttheme;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.core.Writer;

public class ListMenuThemeItem extends org.elaya.page.listmenu.ListMenuThemeItem {

	@Override
	public void header(Writer pwriter,String ptitle) throws IOException {
		pwriter.print("<div class='listmenu_title'>"+escape(ptitle)+"</div><div class='listmenu_header'>");
	}

	@Override
	public void preItem(Writer pwriter,String domId) throws IOException 
	{
		pwriter.print("<div "+property("id",domId)+" class='listmenu_item'>");
	}

	@Override
	public void preItemSelected(Writer pwriter,String domId) throws IOException 
	{
		pwriter.print("<div "+property("id",domId)+"class='listmenu_item_selected'>");
	}

	
	@Override
	public void linkItem(Writer pwriter,String ptext, String purl,String delUrl,String editUrl,Object data) throws IOException {
		if(delUrl!=null && !delUrl.isEmpty()){
			pwriter.print("<img onclick='return parentNode._control.onDelButtonPressed(this);' "+pwriter.propertyF("_data",data)+pwriter.property("src",pwriter.getImgUrl("del.png"))+" />");
		}
		if(editUrl!=null && !editUrl.isEmpty()){
			pwriter.print("<a href='"+editUrl+"'><img src='edit.png'></a>");
		}
		pwriter.print("<a class='listitem_link' "+property("href",purl)+">"+escape(ptext)+"</a>");
	}

		
	@Override
	public void postItem(Writer pwriter) throws IOException {
		pwriter.print("</div>");
	}

	@Override
	public void footer(Writer pwriter) throws IOException {
		pwriter.print("</div>");
	}
	
	@Override
	public void getCssFiles(Set<String> pfiles)
	{
		pfiles.add("listmenu.css");
	}

	@Override
	public void groupHeader(Writer pwriter,String ptitle) throws IOException {
		pwriter.print("<div class='listmenu_grouptitle'>"+escape(ptitle)+"</div><div class='listmenu_groupheader'>");
	}

	@Override
	public void groupFooter(Writer pwriter) throws IOException {
		pwriter.print("</div>");
		
	}

}
