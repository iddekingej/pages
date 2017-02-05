package org.elaya.page.defaulttheme;

import java.io.IOException;

import org.elaya.page.core.Writer;

public class TabPageTheme extends org.elaya.page.widget.element.TabPageTheme {

	@Override
	public void pageHeader(Writer pwriter, String pdomId) throws IOException {
		pwriter.print("<table class='tabpage' "+property("id",pdomId)+"><tr>");
	}

	@Override
	public void headerEnd(Writer pwriter,int ptabs) throws IOException {
		pwriter.print("<td style='width:100%;margin:0px;padding:0px;'></td></tr><tr><td class='tabpage_pagecontainer' "+property("colspan",ptabs+1)+">");

	}

	@Override
	public void pageFooter(Writer pwriter) throws IOException {
		pwriter.print("</td></tr></table>");
	}

	@Override
	public void tabItem(Writer pwriter, String ptitle,String pdomId) throws IOException {
		pwriter.print("<td "+this.classProperty("tabpage_tab")+this.property("id", pdomId)+">"+escape(ptitle)+"</td>");
	}

	@Override
	public void pageItemHeader(Writer pwriter, String pdomId) throws IOException {
		pwriter.print("<div class='tabpage_item' "+property("id",pdomId)+">");		
	}

	@Override
	public void pageItemFooter(Writer pwriter) throws IOException {
		pwriter.print("</div>");
		
	}

}
