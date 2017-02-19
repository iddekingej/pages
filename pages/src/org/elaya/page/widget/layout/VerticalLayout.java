package org.elaya.page.widget.layout;

import java.io.IOException;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Element;

public class VerticalLayout extends Layout {

	public VerticalLayout() {
		super();
	}

	@Override
	protected void preElement(int id,Writer pwriter,Data data,Element<?> pelement) throws IOException
	{
			themeItem.verticalItemHeader(pwriter,pelement.getHorizontalAlign(),pelement.getVerticalAlign(),pelement.getLayoutWidth(),pelement.getLayoutHeight());		
	}
	
	@Override
	protected void postElement(int id,Writer pwriter,Data data,Element<?> pelement) throws IOException
	{
		themeItem.verticalItemFooter(pwriter);
	}
	@Override
	public void displayElement(int id,Writer writer,Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{
			themeItem.verticalHeader(writer);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public void displayElementFooter(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException {
		try{
			themeItem.verticalFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	

}
