package org.elaya.page.widget.listmenu;

import java.io.IOException;

import org.elaya.page.core.Data;
import org.elaya.page.core.KeyNotFoundException;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.Element;

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
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException{
		try{			
			themeItem.groupHeader(pwriter,pwriter.replaceVariables(data,title));
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
	
	
	@Override
	protected void preElement(int id,Writer writer,Data data,Element<?> element) throws IOException, KeyNotFoundException 
	{
		String domId=getDomId(id);
		if(element instanceof ListMenuItem && getParent() instanceof ListMenu){
			String selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
			Object selectedValue=data.get(selectionVariable);
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
	public void displayElementFooter(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException{
	try{
		themeItem.groupFooter(pwriter);
	}catch(Exception e){
		throw new DisplayException(e);
	}
	}

}
