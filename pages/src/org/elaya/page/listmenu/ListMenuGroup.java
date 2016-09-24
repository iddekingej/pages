package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class ListMenuGroup extends BuildinListMenuItem {

	private String title;
	
	public void setTitle(String p_title)
	{
		title=p_title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public ListMenuGroup() {
		super();
	}
	
	@Override
	public boolean checkElement(Element<?> p_element)
	{
		return p_element instanceof ListMenuItem;
	}

	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.groupHeader(p_writer,replaceVariables(l_data,title));		
		Object l_selectedValue=null;
		if(getParent() instanceof ListMenu){
			String l_selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
			l_selectedValue=l_data.get(l_selectionVariable);
			
		}

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
		themeItem.groupFooter(p_writer);		

	}

}
