package org.elaya.page.listmenu;

import org.elaya.page.Element;

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
	public void display(Object p_value) throws Exception {
		themeItem.groupHeader(title);
		for(Element<?> l_element:getElements()){
			
			if(l_element instanceof ListMenuItem){
				String l_value=((ListMenuItem<?>)l_element).getValue();
				if( (l_value != null)? l_value.equals(p_value):false){
					themeItem.preItemSelected();
				} else {
					themeItem.preItem();
				}
				l_element.display(p_value);
				themeItem.postItem();
			}

		}
		themeItem.groupFooter();		

	}

}
