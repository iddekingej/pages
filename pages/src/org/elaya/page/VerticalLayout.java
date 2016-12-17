package org.elaya.page;

import java.io.IOException;
import org.elaya.page.data.Data;

public class VerticalLayout extends Layout {

	public VerticalLayout() {
		super();
	}

	@Override
	protected void preElement(Writer pwriter,Element<?> pelement) throws IOException
	{
			themeItem.verticalItemHeader(pwriter,pelement.getHorizontalAlign(),pelement.getVerticalAlign(),pelement.getLayoutWidth(),pelement.getLayoutHeight());		
	}
	
	@Override
	protected void postElement(Writer pwriter,Element<?> pelement) throws IOException
	{
		themeItem.verticalItemFooter(pwriter);
	}
	@Override
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			Data data=getData(pdata);
			themeItem.verticalHeader(pwriter);
			displaySubElements(pwriter,data);
			themeItem.verticalFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

}
