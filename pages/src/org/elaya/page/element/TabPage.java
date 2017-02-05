package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;

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
	public void displayElement(int pid, Writer pwriter, Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			themeItem.pageItemHeader(pwriter, getDomId(pid));
		}catch(Exception e)
		{
			throw new DisplayException("",e);
		}		
	}
	
	@Override
	public void displayElementFooter(int pid,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException
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
