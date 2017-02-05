package org.elaya.page.widget.element;

import java.io.IOException;

import org.elaya.page.core.JSWriter;
import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.widget.PageElement;
import org.json.JSONException;

public class Menu extends PageElement<ElementThemeItem> {
	private String title;
	private MenuState state=MenuState.NORMAL;
	
	public String getTitle(){ return title;}
	public void setTitle(String ptitle){ title=ptitle;}
	public MenuState getState(){ return state;}
	public void setState(MenuState pstate){ state=pstate;}
	
	
	@Override
	public void displayElement(int id,Writer writer, Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{
			themeItem.menu(writer, getDomId(id),writer.replaceVariables(data,title));
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override
	protected void makeSetupJs(JSWriter writer,Data data) throws IOException, JSONException
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
