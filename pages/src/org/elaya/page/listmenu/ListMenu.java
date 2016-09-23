package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class ListMenu extends PageElement<ListMenuThemeItem> {

	private String selectionVariable="";
	private String title="";
	
	public void setTitle(String p_title)
	{
		title=p_title;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setSelectionVariable(String p_selectionVariable){
		selectionVariable=p_selectionVariable;
	}
	
	public String getSelectionVariable()
	{
		return selectionVariable;
	}
	
	@Override
	public boolean checkElement(Element<?> p_element)
	{
		return p_element instanceof ListMenuItem;
	}
	
	
	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);		
		Object l_selectedValue=null;
		if(selectionVariable.length()>0){
			l_selectedValue=l_data.get(selectionVariable);
		}
		themeItem.header(p_writer,replaceVariables(l_data,title));
		for(Element<?> l_element:getElements()){
			
			if(l_element instanceof ListMenuItem){
				if(l_element.checkCondition(l_data)){
					String l_value=((ListMenuItem<?>)l_element).getValue();
					if( (l_value != null)? l_value.equals(l_selectedValue):false){
						themeItem.preItemSelected(p_writer);
					} else {
						themeItem.preItem(p_writer);
					}
					l_element.display(p_writer,l_data);
					themeItem.postItem(p_writer);
				}
			}

		}
		themeItem.footer(p_writer);
	}

	@Override
	public String getThemeName() {
		return "ListMenuThemeItem";
	}

}
