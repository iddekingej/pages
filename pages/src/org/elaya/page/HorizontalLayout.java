package org.elaya.page;

import java.io.IOException;
import org.elaya.page.data.Data;


public class HorizontalLayout extends Layout{

	@Override
	public void preElement(Writer pwriter,Data data,Element<?> pelement) throws IOException
	{
			themeItem.horizontalItemHeader(pwriter,pelement.getHorizontalAlign(),pelement.getVerticalAlign(),pelement.getLayoutWidth(),pelement.getLayoutHeight());
	}
	
	@Override
	public void postElement(Writer pwriter,Data data,Element<?> pelement) throws IOException{
		themeItem.horizontalItemFooter(pwriter);
	}
	
	@Override
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException 
	{
		try{
			themeItem.horizontalHeader(pwriter);
		}catch(Exception e){
			throw new DisplayException(e);
		}
	}
	
	@Override
	public void displayElementFooter(int id,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException 
	{
		try{
			themeItem.horizontalFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException(e);
		}
	}
}
