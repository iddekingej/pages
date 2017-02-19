package org.elaya.page.widget.element;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.PageElement;

public class TabPage extends  PageElement<TabPageTheme>{
	private String tabTitle;
	
	public void setTabTitle(String ptabTitle)
	{
		tabTitle=ptabTitle;
	}
	
	public String getTabTitle()
	{
		return tabTitle;
	}
	@Override
	public void displayElement(int pid, Writer pwriter, Data pdata) throws org.elaya.page.widget.Element.DisplayException {
		try{
			themeItem.pageItemHeader(pwriter, getDomId(pid));
		}catch(Exception e)
		{
			throw new DisplayException("",e);
		}		
	}
	
	@Override
	public void displayElementFooter(int pid,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException
	{
		try{
			themeItem.pageItemFooter(pwriter);
		}catch(Exception e)
		{
			throw new DisplayException("",e);
		}
	}


	
	@Override
	public String getThemeName() {
		return "TabPageTheme";
	}

}
