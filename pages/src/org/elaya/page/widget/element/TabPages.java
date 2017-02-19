package org.elaya.page.widget.element;

import java.io.IOException;

import org.elaya.page.core.Data;
import org.elaya.page.core.JSWriter;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Element;
import org.elaya.page.widget.PageElement;
import org.json.JSONException;

public class TabPages extends PageElement<TabPageTheme> {

	private int defaultTab;
	
	public void setDefaultTab(Integer pdefaultTab)
	{
		defaultTab=pdefaultTab;
	}
	
	public int getDefaultTab()
	{
		return defaultTab;
	}
	
	@Override
	public void displayElement(int id, Writer writer, Data data) throws DisplayException {
		String tabName;
		String domId=getDomId(id);
		int tabCnt;
		try{
			themeItem.pageHeader(writer,domId);
			tabCnt=0;
			for(Element<?> element:getElements()){
				if(element instanceof TabPage){
					tabName=((TabPage)element).getTabTitle();
				} else {
					tabName="";
				}
				themeItem.tabItem(writer, tabName, domId+"_t_"+tabCnt);
				tabCnt++;
			}
			themeItem.headerEnd(writer,getElements().size());
			
		}catch(Exception e)
		{
			throw new DisplayException("",e);
		}
	}
	
	@Override
	public void displayElementFooter(int pid,Writer pwriter,Data pdata) throws DisplayException 
	{
		try{
			themeItem.pageFooter(pwriter);
		}catch(Exception e)
		{
			throw new DisplayException("",e);
		}
	}

	@Override
	protected void makeSetupJs(JSWriter writer,Data data) throws IOException, JSONException
	{
		writer.objVar("defaultTab", defaultTab);
	}
	
	@Override
	protected boolean checkElement(Element<?> pelement)
	{
		
		return pelement instanceof TabPage;
	}
	
	@Override
	public String getJsClassName() {
		return "TTabPages";
	}	
	
	@Override
	public String getThemeName() {
		return "TabPageTheme";
		
	}

}
