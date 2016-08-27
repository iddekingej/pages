package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.Theme;

public class LayoutThemeItem extends org.elaya.page.LayoutThemeItem {

	public LayoutThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}
	
	public void getCssFiles(Set<String> p_files){ 
		p_files.add("page.css");
	}
	
	public void HorizontalHeader() throws IOException {
		print("<table><tr>");
	}

	@Override
	public void HorizontalItemHeader() throws IOException {
		// TODO Auto-generated method stub
		print("<td>");
	}

	@Override
	public void HorizontalItemFooter() throws IOException {
		print("</td>");

	}

	@Override
	public void HorizontalFooter() throws IOException {
		print("</tr></table>");

	}

}
