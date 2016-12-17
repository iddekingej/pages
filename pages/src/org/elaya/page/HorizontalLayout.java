package org.elaya.page;

import java.io.IOException;
import org.elaya.page.data.Data;


public class HorizontalLayout extends Layout{

	public HorizontalLayout()
	{
		super();
	}
	
	@Override
	public void preElement(Writer pwriter,Element<?> pelement) throws IOException
	{
			themeItem.horizontalItemHeader(pwriter,pelement.getHorizontalAlign(),pelement.getVerticalAlign(),pelement.getLayoutWidth(),pelement.getLayoutHeight());
	}
	
	@Override
	public void postElement(Writer pwriter,Element<?> pelement) throws IOException{
		themeItem.horizontalItemFooter(pwriter);
	}
	@Override
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException 
	{
		try{
			Data data=getData(pdata);
			themeItem.horizontalHeader(pwriter);
			displaySubElements(pwriter,data);
			themeItem.horizontalFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
}
