package org.elaya.page.layout;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.Element.DisplayException;
import org.elaya.page.data.Data;

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
