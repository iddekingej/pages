package org.elaya.page.listmenu;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.data.Data.KeyNotFoundException;

public class ListMenu extends PageElement<ListMenuThemeItem> {

	private String selectionVariable="";
	private String title="";
	
	public ListMenu()
	{
		super();
		setIsNamespace(true);
	}
	
	public void setTitle(String ptitle)
	{
		title=ptitle;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setSelectionVariable(String pselectionVariable){
		selectionVariable=pselectionVariable;
	}
	
	public String getSelectionVariable()
	{
		return selectionVariable;
	}
	
		
	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException{
		try{			
			themeItem.header(pwriter,pwriter.replaceVariables(data,title));
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	protected void preElement(int id,Writer writer,Data data,Element<?> element) throws IOException, KeyNotFoundException 
	{
		String domId=getDomId(id);
		if(element instanceof ListMenuItem && selectionVariable.length()>0 ){
			Object selectedValue=null;
			if(selectionVariable.length()>0){
				selectedValue=data.get(selectionVariable);
			}
			String value=((ListMenuItem<?>)element).getValue();
			if( (value != null)? value.equals(selectedValue):false){
				themeItem.preItemSelected(writer,domId);
			} else {
				themeItem.preItem(writer,domId);
			}
		} else {
			themeItem.preItem(writer,domId);
		}
	}
	@Override
	protected void postElement(int id,Writer writer,Data data,Element<?> element) throws IOException
	{		
		themeItem.postItem(writer);
	}
	
	@Override
	public void displayElementFooter(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException{
		try{
			themeItem.footer(pwriter);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	@Override
	public String getJsClassName() {
		return "TListMenu";
	}

	@Override
	public String getThemeName() {
		return "ListMenuThemeItem";
	}

} 
