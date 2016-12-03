package org.elaya.page.listmenu;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.data.LinkMenuData;

public class DynamicLinkMenuItem extends BuildinListMenuItem {

	public DynamicLinkMenuItem() {
		super();
	}

	@Override
	public void display(Writer p_writer, Data p_data) throws Exception {
		
		Data l_data=getData(p_data);
		Object l_value=this.getValueByName(l_data);
		Object l_selectedValue=null;
		if(getParent() instanceof ListMenu){
			String l_selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
			l_selectedValue=l_data.get(l_selectionVariable);
			
		}
		if(l_value instanceof Iterable){
			for(Object l_object:(Iterable<?>)l_value){
				if(l_object instanceof LinkMenuData){
					LinkMenuData l_menuData=(LinkMenuData)l_object;
					if( (l_menuData.getId() != null)? l_menuData.getId().equals(l_selectedValue):false){
						themeItem.preItemSelected(p_writer);
					} else {
						themeItem.preItem(p_writer);
					}
					
					themeItem.linkItem(p_writer,getDomId(),l_menuData.getText(),p_writer.procesUrl(l_menuData.getUrlText()));
					themeItem.postItem(p_writer);
				}
					
			}
			
		}
	}

}
