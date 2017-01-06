package org.elaya.page.defaulttheme;

import java.io.IOException;
import java.util.Set;
import org.elaya.page.HorizontalAlign;
import org.elaya.page.VerticalAlign;
import org.elaya.page.Writer;

public class LayoutThemeItem extends org.elaya.page.layout.LayoutThemeItem {

	private void makeCell(Writer pwriter,String pclassPrefix,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight) throws IOException
	{
		String style="";
		String layoutWidth=str(playoutWidth);
		String layoutHeight=str(playoutHeight);
		if(layoutWidth.length()>0){
			style += "width:"+layoutWidth+";";
		}
		if(layoutHeight.length()>0){
			style += "height:"+layoutHeight+";";
		}
		pwriter.print("<td "+ property("class",pclassPrefix+"page_layout")+property("align",phorizontalAlign.gettagValue())+property("valign",pverticalAlign)+propertyF("style",style)+">");
	}

	@Override
	public void getCssFiles(Set<String> pfiles){ 
		pfiles.add("page.css");
	}
	
	@Override
	public void verticalHeader(Writer pwriter) throws IOException{
		pwriter.print("<table style='width:100%;height:100%'>");
	}
	
	@Override
	public void verticalItemHeader(Writer pwriter,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight)throws IOException{
		pwriter.print("<tr>");
		makeCell(pwriter,"",phorizontalAlign,pverticalAlign,playoutWidth,playoutHeight);
	}
	
	@Override
	public void verticalItemFooter(Writer pwriter) throws IOException{
		pwriter.print("</td></tr>");
	}
	
	@Override
	public void verticalFooter(Writer pwriter) throws IOException{
		pwriter.print("</table>");
	}
	
	@Override
	public void horizontalHeader(Writer pwriter) throws IOException {
		pwriter.print("<table style='width:100%'><tr>");
	}

	
	@Override
	public void horizontalItemHeader(Writer pwriter,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight) throws IOException {
		makeCell(pwriter,"",phorizontalAlign,pverticalAlign,playoutWidth,playoutHeight);
	}

	@Override
	public void horizontalItemFooter(Writer pwriter) throws IOException {
		pwriter.print("</td>");

	}

	@Override
	public void horizontalFooter(Writer pwriter) throws IOException {
		pwriter.print("</tr></table>");

	}

	@Override
	public void gridHeader(Writer pwriter) throws IOException {
		pwriter.print("<table>");
	}

	@Override
	public void gridRowHeader(Writer pwriter) throws IOException {
		pwriter.print("<tr>");
		
	}

	@Override
	public void gridItemHeader(Writer pwriter,String pclassPrefix,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight) throws IOException {
		makeCell(pwriter,pclassPrefix,phorizontalAlign,pverticalAlign,playoutWidth,playoutHeight);
	}

	@Override
	public void gridItemFooter(Writer pwriter) throws IOException {
		pwriter.print("</td>");
		
	}

	@Override
	public void gridRowFooter(Writer pwriter) throws IOException {
		pwriter.print("</tr>");
	}

	@Override
	public void gridFooter(Writer pwriter) throws IOException {
		pwriter.print("</table>");
	}

	@Override
	public void tableHeader(Writer writer, String className) throws IOException {
		writer.print("<table "+property("class",className)+">");
		
	}

	@Override
	public void tableRowHeader(Writer writer) throws IOException {
		writer.print("<tr >");
	}

	@Override
	public void tableCellHeader(Writer writer,String classNamePrefix,HorizontalAlign phorizontalAlign,VerticalAlign pverticalAlign,String playoutWidth,String playoutHeight) throws IOException{
		makeCell(writer,classNamePrefix,phorizontalAlign,pverticalAlign,playoutWidth,playoutHeight);
	}

	@Override
	public void tableCellFooter(Writer writer) throws IOException {
		writer.print("</td>");
		
	}

	@Override
	public void tableRowFooter(Writer writer) throws IOException {
		writer.print("</tr>");
	}

	@Override
	public void tableFooter(Writer writer) throws IOException {
		writer.print("</table>");
	}

	
	
}
