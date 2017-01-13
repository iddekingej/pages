package org.elaya.page.listmenu;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;


public class DynamicLinkMenuItem extends BuildinListMenuItem {

	public DynamicLinkMenuItem() {
		super();
	}

	@Override
	public void displayElement(int id,Writer pwriter, Data data) throws org.elaya.page.Element.DisplayException  {
		try{
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
						themeItem.linkItem(pwriter,getDomId(id),menuData.getText(),pwriter.processUrl(data,menuData.getUrlText()),pwriter.processUrl(data,menuData.getDelUrl()),pwriter.processUrl(data,menuData.getEditUrl()));
						themeItem.postItem(pwriter);
					}

				}

			}
		
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}
}
