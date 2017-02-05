package org.elaya.page.widget.layout;

import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.widget.Element;

public class TableLayout extends Layout {


	@Override
	public void displayElement(int id, Writer stream, Data data) throws Element.DisplayException {
		try{
			themeItem.tableHeader(stream, getClassPrefix());
		}catch(Exception  e){
			throw new Element.DisplayException(e);
		}
	}

	@Override
	public void displayElementFooter(int id,Writer stream,Data data) throws Element.DisplayException  
	{
		try{
			themeItem.tableFooter(stream);
		}catch(Exception  e){
			throw new Element.DisplayException(e);
		}
		

	}
	
}
