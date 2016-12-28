package org.elaya.page.listmenu;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.data.Data.KeyNotFoundException;

public class ListMenuGroup extends BuildinListMenuItem {

	private String title;
	
	public void setTitle(String ptitle)
	{
		title=ptitle;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	@Override
	public boolean checkElement(Element<?> pelement)
	{
		return pelement instanceof ListMenuItem;
	}
	
	

	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException{
		try{			
			themeItem.groupHeader(pwriter,replaceVariables(data,title));
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	
	@Override
	protected void preElement(Writer writer,Data data,Element<?> element) throws IOException, KeyNotFoundException 
	{
		if(element instanceof ListMenuItem && getParent() instanceof ListMenu){
			String selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
			Object selectedValue=data.get(selectionVariable);
			String value=((ListMenuItem<?>)element).getValue();
			if( (value != null)? value.equals(selectedValue):false){
				themeItem.preItemSelected(writer);
			} else {
				themeItem.preItem(writer);
			}
		} else {
			themeItem.preItem(writer);
		}
	}
	
	@Override
	protected void postElement(Writer writer,Data data,Element<?> element) throws IOException
	{		
		themeItem.postItem(writer);
	}
	
	@Override
	public void displayElementFooter(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException{
	try{
		themeItem.groupFooter(pwriter);
	}catch(Exception e){
		throw new DisplayException(e);
	}
	}

}
