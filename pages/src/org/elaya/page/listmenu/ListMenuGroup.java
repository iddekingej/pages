package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.Errors;
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
		Object l_object=null;
		String l_selectionVariable="";
		if(getParent() instanceof ListMenu){
			l_selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
			l_selectionVariable=replaceVariables(l_data,l_selectionVariable);
		}
		if(l_selectionVariable.length()>0){
			if(!l_data.containsKey(l_selectionVariable)){
				throw new Errors.ValueNotFound(l_selectionVariable);
			}
			l_object=l_data.get(l_selectionVariable);
		}		
		for(Element<?> l_element:getElements()){
			
			if(l_element instanceof ListMenuItem){
				if(l_element.checkCondition(l_data)){
					String l_value=((ListMenuItem<?>)l_element).getValue();
					if( (l_value != null)? l_value.equals(l_object):false){
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
