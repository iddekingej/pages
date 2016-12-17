package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class Menu extends PageElement<ElementThemeItem> {
	private String title;
	private MenuState state=MenuState.NORMAL;
	
	public String getTitle(){ return title;}
	public void setTitle(String ptitle){ title=ptitle;}
	public MenuState getState(){ return state;}
	public void setState(MenuState pstate){ state=pstate;}
	
	
	@Override
	public void display(Writer pstream, Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			Data data=getData(pdata);
			themeItem.menu(pstream, getDomId(), replaceVariables(data,title));
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override
	protected void makeSetupJs(Writer writer,Data data) throws Exception
	{
		writer.print("this.setState("+writer.js_toString(state.getJsState())+")\n");
	}
	
	@Override
	public String getJsClassName() {
		return "TMenu";
	}	
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
