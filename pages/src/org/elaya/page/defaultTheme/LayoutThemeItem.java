package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.HorizontalAlign;
import org.elaya.page.Theme;
import org.elaya.page.VerticalAlign;
import org.elaya.page.Writer;

public class LayoutThemeItem extends org.elaya.page.LayoutThemeItem {

	private void makeCell(Writer p_writer,String p_classPrefix,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException
	{
		String l_style="";
		String l_layoutWidth=str(p_layoutWidth);
		String l_layoutHeight=str(p_layoutHeight);
		if(l_layoutWidth.length()>0) l_style += "width:"+l_layoutWidth+";";
		if(l_layoutHeight.length()>0) l_style += "height:"+l_layoutHeight+";";
		p_writer.print("<td "+ property("class",p_classPrefix+"page_layout")+property("align",p_horizontalAlign.gettagValue())+property("valign",p_verticalAlign)+propertyF("style",l_style)+">");
	}

	public void getCssFiles(Set<String> p_files){ 
		p_files.add("page.css");
	}
	
	public void verticalHeader(Writer p_writer) throws IOException{
		p_writer.print("<table style='width:100%;height:100%'>");
	}
	
	public void verticalItemHeader(Writer p_writer,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight)throws IOException{
		p_writer.print("<tr>");
		makeCell(p_writer,"",p_horizontalAlign,p_verticalAlign,p_layoutWidth,p_layoutHeight);
	}
	
	public void verticalItemFooter(Writer p_writer) throws IOException{
		p_writer.print("</td></tr>");
	}
	
	public void verticalFooter(Writer p_writer) throws IOException{
		p_writer.print("</table>");
	}
	
	public void HorizontalHeader(Writer p_writer) throws IOException {
		p_writer.print("<table style='width:100%'><tr>");
	}

	
	@Override
	public void HorizontalItemHeader(Writer p_writer,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException {
		makeCell(p_writer,"",p_horizontalAlign,p_verticalAlign,p_layoutWidth,p_layoutHeight);
	}

	@Override
	public void HorizontalItemFooter(Writer p_writer) throws IOException {
		p_writer.print("</td>");

	}

	@Override
	public void HorizontalFooter(Writer p_writer) throws IOException {
		p_writer.print("</tr></table>");

	}

	@Override
	public void gridHeader(Writer p_writer) throws IOException {
		p_writer.print("<table>");
	}

	@Override
	public void gridRowHeader(Writer p_writer) throws IOException {
		p_writer.print("<tr>");
		
	}

	@Override
	public void gridItemHeader(Writer p_writer,String p_classPrefix,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException {
		makeCell(p_writer,p_classPrefix,p_horizontalAlign,p_verticalAlign,p_layoutWidth,p_layoutHeight);
	}

	@Override
	public void gridItemFooter(Writer p_writer) throws IOException {
		p_writer.print("</td>");
		
	}

	@Override
	public void gridRowFooter(Writer p_writer) throws IOException {
		p_writer.print("</tr>");
	}

	@Override
	public void gridFooter(Writer p_writer) throws IOException {
		p_writer.print("</table>");
	}

}
