package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.PageElement;

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
	public void display() throws Exception {
		Object l_object=null;
		if(selectionVariable.length()>0){
			if(!hasValue(selectionVariable)){
				throw new Errors.ValueNotFound(selectionVariable);
			}
			l_object=getValue(selectionVariable);
		}
		themeItem.header(title);
		for(Element<?> l_element:getElements()){
			
			if(l_element instanceof ListMenuItem){
				String l_value=((ListMenuItem<?>)l_element).getValue();
				if( (l_value != null)? l_value.equals(l_object):false){
					themeItem.preItemSelected();
				} else {
					themeItem.preItem();
				}
				l_element.display(l_object);
				themeItem.postItem();
			}

		}
		themeItem.footer();
	}

	@Override
	public String getThemeName() {
		return "ListMenuThemeItem";
	}

}
