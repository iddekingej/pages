package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.Theme;

public class TableThemeItem extends org.elaya.page.table.TableThemeItem {

	public TableThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	
	public void getCssFiles(Set<String> p_files)
	{
		p_files.add("table.css");
	}
	
	@Override
	public void tableHeader(String p_domId) throws IOException {
		print("<table "+classProperty("table_header")+property("id",p_domId)+">");
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
		print("<td "+classProperty("table_header_cell")+">"+escape(p_title)+"</td>");

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
	public void staticItem(String p_domId,Object p_text) throws IOException {
		print(escape(p_text));
	}

	@Override
	public void linkItem(String p_url,String p_text) throws IOException {
		print("<a "+property("href",p_url)+">"+escape(p_text)+"</a>");
	}

	@Override
	public void rowFooter() throws IOException {
		print("</tr>");
		
	}

	@Override
	public void itemHeader() throws IOException {
		print("<td "+classProperty("table_cell")+">");
		
	}

	@Override
	public void itemFooter() throws IOException {
		print("</td>");
		
	}

}
