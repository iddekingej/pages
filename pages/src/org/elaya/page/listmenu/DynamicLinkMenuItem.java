package org.elaya.page.listmenu;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.data.LinkMenuData;


public class DynamicLinkMenuItem extends BuildinListMenuItem {

	public DynamicLinkMenuItem() {
		super();
	}

	@Override
	public void display(Writer pwriter, Data pdata) throws org.elaya.page.Element.DisplayException  {
		try{
			Data data=getData(pdata);
			Object value=this.getValueByName(data);
			Object selectedValue=null;
			if(getParent() instanceof ListMenu){
				String selectionVariable = ((ListMenu)getParent()).getSelectionVariable();
				selectedValue=data.get(selectionVariable);

			}
			if(value instanceof Iterable){
				for(Object object:(Iterable<?>)value){
					if(object instanceof LinkMenuData){
						LinkMenuData menuData=(LinkMenuData)object;
						if( (menuData.getId() != null)? menuData.getId().equals(selectedValue):false){
							themeItem.preItemSelected(pwriter);
						} else {
							themeItem.preItem(pwriter);
						}

						themeItem.linkItem(pwriter,getDomId(),menuData.getText(),pwriter.procesUrl(menuData.getUrlText()));
						themeItem.postItem(pwriter);
					}

				}

			}
		
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
}
