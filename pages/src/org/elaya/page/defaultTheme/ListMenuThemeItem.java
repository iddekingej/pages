package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.Theme;

public class ListMenuThemeItem extends org.elaya.page.listmenu.ListMenuThemeItem {

	public ListMenuThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void header(String p_title) throws IOException {
		print("<div class='listmenu_title'>"+escape(p_title)+"</div><div class='listmenu_header'>");
	}

	@Override
	public void preItem() throws IOException 
	{
		print("<div class='listmenu_item'>");
	}

	@Override
	public void preItemSelected() throws IOException 
	{
		print("<div class='listmenu_item_selected'>");
	}

	
	@Override
	public void linkItem(String p_domId, String p_text, String p_url) throws IOException {
		print("<a class='listitem_link' "+property("id",p_domId)+property("href",p_url)+">"+escape(p_text)+"</a>");
	}

	@Override
	public void postItem() throws IOException {
		print("</div>");
	}

	@Override
	public void footer() throws IOException {
		print("</div>");
	}
	
	public void getCssFiles(Set<String> p_files)
	{
		p_files.add("listmenu.css");
	}

	@Override
	public void groupHeader(String p_title) throws IOException {
		print("<div class='listmenu_grouptitle'>"+escape(p_title)+"</div><div class='listmenu_groupheader'>");
	}

	@Override
	public void groupFooter() throws IOException {
		print("</div>");
		
	}

}
