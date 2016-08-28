package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.Theme;

public class TableThemeItem extends org.elaya.page.table.TableThemeItem {

	public TableThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	public void addCssFile(Set<String> p_files)
	{
		p_files.add("table.css");
	}
	
	@Override
	public void tableHeader(String p_domId) throws IOException {
		print("<table "+property("id",p_domId)+">");
	}

	@Override
	public void tableFooter() throws IOException {
		print("</table>");
	}

	@Override
	public void titleHeader() throws IOException {
		print("<tr>");

	}

	@Override
	public void title(String p_title) throws IOException {
		print("<td>"+escape(p_title)+"</td>");

	}

	@Override
	public void titleFooter() throws IOException {
		print("</tr>");
	}

	@Override
	public void rowHeader() throws IOException {
		print("<tr>");
	}

	@Override
	public void staticItem(String p_text) throws IOException {
		print("<td>"+escape(p_text)+"</td>");
	}

	@Override
	public void linkItem(String p_text, String p_url) throws IOException {
		print("<a "+property("href",p_url)+">"+escape(p_text)+"</a>");
	}

}
