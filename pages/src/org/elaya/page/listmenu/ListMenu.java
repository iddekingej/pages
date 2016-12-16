package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

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
	public boolean checkElement(Element<?> pelement)
	{
		return pelement instanceof ListMenuItem;
	}
	
	private void handleCheckCondition(Writer writer,Object selectedValue,Element<?> element,Data data) throws Exception
	{
		if(element.checkCondition(data)){
			String value=((ListMenuItem<?>)element).getValue();
			if( (value != null)? value.equals(selectedValue):false){
				themeItem.preItemSelected(writer);
			} else {
				themeItem.preItem(writer);
			}
			element.display(writer,data);
			themeItem.postItem(writer);
		}		
	}
	
	@Override
	public void display(Writer pwriter,Data pdata) throws Exception {
		Data data=getData(pdata);		
		Object selectedValue=null;
		if(selectionVariable.length()>0){
			selectedValue=data.get(selectionVariable);
		}
		themeItem.header(pwriter,replaceVariables(data,title));
		for(Element<?> element:getElements()){
			
			if(element instanceof ListMenuItem){
				handleCheckCondition(pwriter,selectedValue,element,data);
			}

		}
		themeItem.footer(pwriter);
	}

	@Override
	public String getThemeName() {
		return "ListMenuThemeItem";
	}

} 
