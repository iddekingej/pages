package org.elaya.page.listmenu;

import org.elaya.page.Element;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

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
	public void display(Writer pwriter,Data pdata) throws Exception {
		Data data=getData(pdata);
		themeItem.groupHeader(pwriter,replaceVariables(data,title));		
		Object selectedValue=null;
		if(getParent() instanceof ListMenu){
			String selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
			selectedValue=data.get(selectionVariable);
			
		}

		for(Element<?> element:getElements()){
			
			if(element instanceof ListMenuItem){
				if(element.checkCondition(data)){
					String value=((ListMenuItem<?>)element).getValue();
					if( (value != null)? value.equals(selectedValue):false){
						themeItem.preItemSelected(pwriter);
					} else {
						themeItem.preItem(pwriter);
					}
					element.display(pwriter,data);
					themeItem.postItem(pwriter);
				}
			}

		}
		themeItem.groupFooter(pwriter);		

	}

}
