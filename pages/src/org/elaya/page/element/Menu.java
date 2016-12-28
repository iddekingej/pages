package org.elaya.page.element;

import java.io.IOException;

import org.elaya.page.JSWriter;
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
	public void displayElement(int id,Writer writer, Data data) throws org.elaya.page.Element.DisplayException {
		try{
			themeItem.menu(writer, getDomId(id), replaceVariables(data,title));
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override
	protected void makeSetupJs(JSWriter writer,Data data) throws IOException
	{
		writer.print("this.setState("+writer.toJsString(state.getJsState())+")\n");
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
