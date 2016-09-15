package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.PageElement;
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
	public void display(Data p_data) throws Exception {
		Data l_data=getData(p_data);
		Object l_object=null;

		if(selectionVariable.length()>0){
			String l_selectionVariable=replaceVariables(l_data,selectionVariable);
			if(!l_data.containsKey(l_selectionVariable)){
				throw new Errors.ValueNotFound(l_selectionVariable);
			}
			l_object=l_data.get(l_selectionVariable);
		}
		themeItem.header(replaceVariables(l_data,title));
		for(Element<?> l_element:getElements()){
			
			if(l_element instanceof ListMenuItem){
				if(l_element.checkCondition(l_data)){
					String l_value=((ListMenuItem<?>)l_element).getValue();
					if( (l_value != null)? l_value.equals(l_object):false){
						themeItem.preItemSelected();
					} else {
						themeItem.preItem();
					}
					l_element.display(l_data);
					themeItem.postItem();
				}
			}

		}
		themeItem.footer();
	}

	@Override
	public String getThemeName() {
		return "ListMenuThemeItem";
	}

}
