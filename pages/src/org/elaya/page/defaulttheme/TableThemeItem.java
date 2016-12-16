package org.elaya.page.defaulttheme;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.Writer;

public class TableThemeItem extends org.elaya.page.table.TableThemeItem {

	@Override
	public void getCssFiles(Set<String> pfiles)
	{
		pfiles.add("table.css");
	}
	
	@Override
	public void tableHeader(Writer pwriter,String pdomId) throws IOException {
		pwriter.print("<table "+classProperty("table_header")+property("id",pdomId)+">");
	}

	@Override
	public void tableFooter(Writer pwriter) throws IOException {
		pwriter.print("</table>");
	}

	@Override
	public void titleHeader(Writer pwriter) throws IOException {
		pwriter.print("<tr>");
	}

	@Override
	public void title(Writer pwriter,String ptitle) throws IOException {
		pwriter.print("<td "+classProperty("table_header_cell")+">"+escape(ptitle)+"</td>");

	}

	@Override
	public void titleFooter(Writer pwriter) throws IOException {
		pwriter.print("</tr>");
	}
 
	@Override
	public void rowHeader(Writer pwriter) throws IOException {
		pwriter.print("<tr>");
	}

	@Override
	public void staticItem(Writer pwriter,String pdomId,Object ptext) throws IOException {
		pwriter.print(escape(ptext));
	}

	@Override
	public void linkItem(Writer pwriter,String purl,String ptext) throws IOException {
		pwriter.print("<a "+property("href",purl)+">"+escape(ptext)+"</a>");
	}

	@Override
	public void rowFooter(Writer pwriter) throws IOException {
		pwriter.print("</tr>");
		
	}

	@Override
	public void itemHeader(Writer pwriter) throws IOException {
		pwriter.print("<td "+classProperty("table_cell")+">");
		
	}

	@Override
	public void itemFooter(Writer pwriter) throws IOException {
		pwriter.print("</td>");
		
	}

}
