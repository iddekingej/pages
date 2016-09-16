package org.elaya.page.defaultTheme;

import java.io.IOException;
import java.util.Set;

import org.elaya.page.HorizontalAlign;
import org.elaya.page.Theme;
import org.elaya.page.VerticalAlign;

public class LayoutThemeItem extends org.elaya.page.LayoutThemeItem {

	private void makeCell(String p_classPrefix,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException
	{
		String l_style="";
		String l_layoutWidth=str(p_layoutWidth);
		String l_layoutHeight=str(p_layoutHeight);
		if(l_layoutWidth.length()>0) l_style += "width:"+l_layoutWidth+";";
		if(l_layoutHeight.length()>0) l_style += "height:"+l_layoutHeight+";";
		print("<td "+ property("class",p_classPrefix+"page_layout")+property("align",p_horizontalAlign.gettagValue())+property("valign",p_verticalAlign)+propertyF("style",l_style)+">");
	}
	
	public LayoutThemeItem(Theme p_theme) throws IOException {
		super(p_theme);
	}
	
	public void getCssFiles(Set<String> p_files){ 
		p_files.add("page.css");
	}
	
	public void verticalHeader() throws IOException{
		print("<table style='width:100%;height:100%'>");
	}
	
	public void verticalItemHeader(HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight)throws IOException{
		print("<tr>");
		makeCell("",p_horizontalAlign,p_verticalAlign,p_layoutWidth,p_layoutHeight);
	}
	
	public void verticalItemFooter() throws IOException{
		print("</td></tr>");
	}
	
	public void verticalFooter() throws IOException{
		print("</table>");
	}
	
	public void HorizontalHeader() throws IOException {
		print("<table style='width:100%'><tr>");
	}

	
	@Override
	public void HorizontalItemHeader(HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException {
		makeCell("",p_horizontalAlign,p_verticalAlign,p_layoutWidth,p_layoutHeight);
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
	public void gridItemHeader(String p_classPrefix,HorizontalAlign p_horizontalAlign,VerticalAlign p_verticalAlign,String p_layoutWidth,String p_layoutHeight) throws IOException {
		makeCell(p_classPrefix,p_horizontalAlign,p_verticalAlign,p_layoutWidth,p_layoutHeight);
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
