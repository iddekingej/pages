package org.elaya.page.widget.layout;

import java.io.IOException;

import org.elaya.page.core.Data;
import org.elaya.page.core.KeyNotFoundException;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Element;

public class TableRowLayout extends Layout {

	@Override
	protected void preElement(int id,Writer writer,Data data,Element<?> element) throws IOException, KeyNotFoundException 
	{
		themeItem.tableCellHeader(writer,this.getClassPrefix() ,element.getHorizontalAlign(),element.getVerticalAlign(),element.getLayoutWidth(),element.getLayoutHeight());		
	}

	@Override
	protected void postElement(int id,Writer writer,Data data,Element<?> element) throws IOException 
	{
		themeItem.tableCellFooter(writer);		
	}
	
	@Override
	public void displayElement(int id, Writer stream, Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{
			themeItem.tableRowHeader(stream);
		}catch(Exception  e){
			throw new Element.DisplayException(e);
		}
	}
	
	@Override
	public void displayElementFooter(int id,Writer stream,Data data) throws Element.DisplayException  
	{
		try{
			themeItem.tableRowFooter(stream);
		}catch(Exception  e){
			throw new Element.DisplayException(e);
		}
		

	}

}
