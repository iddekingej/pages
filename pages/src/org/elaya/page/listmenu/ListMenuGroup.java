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
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			Data data=getData(pdata);
			themeItem.groupHeader(pwriter,replaceVariables(data,title));		
			Object selectedValue=null;
			if(getParent() instanceof ListMenu){
				String selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
				selectedValue=data.get(selectionVariable);

			}

			for(Element<?> element:getElements()){

				if(element instanceof ListMenuItem){
					handleCheckCondition(pwriter,selectedValue,element,data);
				}

			}
			themeItem.groupFooter(pwriter);		
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

}
