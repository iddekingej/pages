package org.elaya.page.defaulttheme;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.Writer;

public class ListMenuThemeItem extends org.elaya.page.listmenu.ListMenuThemeItem {

	@Override
	public void header(Writer pwriter,String ptitle) throws IOException {
		pwriter.print("<div class='listmenu_title'>"+escape(ptitle)+"</div><div class='listmenu_header'>");
	}

	@Override
	public void preItem(Writer pwriter) throws IOException 
	{
		pwriter.print("<div class='listmenu_item'>");
	}

	@Override
	public void preItemSelected(Writer pwriter) throws IOException 
	{
		pwriter.print("<div class='listmenu_item_selected'>");
	}

	
	@Override
	public void linkItem(Writer pwriter,String pdomId, String ptext, String purl,String delUrl,String editUrl) throws IOException {
		if(delUrl!=null && !delUrl.isEmpty()){
			pwriter.print("<a "+pwriter.property("href", delUrl)+"><img "+pwriter.property("src",pwriter.getImgUrl("del.png"))+" /></a>");
		}
		if(editUrl!=null && !editUrl.isEmpty()){
			pwriter.print("<a href='"+editUrl+"'><img src='edit.png'></a>");
		}
		pwriter.print("<a class='listitem_link' "+property("id",pdomId)+property("href",purl)+">"+escape(ptext)+"</a>");
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
