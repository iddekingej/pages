package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.Theme;
import org.elaya.page.Writer;

public class TableThemeItem extends org.elaya.page.table.TableThemeItem {

	public TableThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}

	
	public void getCssFiles(Set<String> p_files)
	{
		p_files.add("table.css");
	}
	
	@Override
	public void tableHeader(Writer p_writer,String p_domId) throws IOException {
		p_writer.print("<table "+classProperty("table_header")+property("id",p_domId)+">");
	}

	@Override
	public void tableFooter(Writer p_writer) throws IOException {
		p_writer.print("</table>");
	}

	@Override
	public void titleHeader(Writer p_writer) throws IOException {
		p_writer.print("<tr>");
	}

	@Override
	public void title(Writer p_writer,String p_title) throws IOException {
		p_writer.print("<td "+classProperty("table_header_cell")+">"+escape(p_title)+"</td>");

	}

	@Override
	public void titleFooter(Writer p_writer) throws IOException {
		p_writer.print("</tr>");
	}
 
	@Override
	public void rowHeader(Writer p_writer) throws IOException {
		p_writer.print("<tr>");
	}

	@Override
	public void staticItem(Writer p_writer,String p_domId,Object p_text) throws IOException {
		p_writer.print(escape(p_text));
	}

	@Override
	public void linkItem(Writer p_writer,String p_url,String p_text) throws IOException {
		p_writer.print("<a "+property("href",p_url)+">"+escape(p_text)+"</a>");
	}

	@Override
	public void rowFooter(Writer p_writer) throws IOException {
		p_writer.print("</tr>");
		
	}

	@Override
	public void itemHeader(Writer p_writer) throws IOException {
		p_writer.print("<td "+classProperty("table_cell")+">");
		
	}

	@Override
	public void itemFooter(Writer p_writer) throws IOException {
		p_writer.print("</td>");
		
	}

}
