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
	
	public void verticalHeader() throws IOException{
		print("<table>");
	}
	
	public void verticalItemHeader()throws IOException{
		print("<tr><td>");
	}
	
	public void verticalItemFooter() throws IOException{
		print("</td></tr>");
	}
	
	public void verticalFooter() throws IOException{
		print("</table>");
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

	@Override
	public void gridHeader() throws IOException {
		print("<table>");
	}

	@Override
	public void gridRowHeader() throws IOException {
		print("<tr>");
		
	}

	@Override
	public void gridItemHeader() throws IOException {
		print("<td>");
		
	}

	@Override
	public void gridItemFooter() throws IOException {
		print("</td>");
		
	}

	@Override
	public void gridRowFooter() throws IOException {
		print("</tr>");
	}

	@Override
	public void gridFooter() throws IOException {
		print("</table>");
	}

}
