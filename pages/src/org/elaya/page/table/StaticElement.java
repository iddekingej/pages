package org.elaya.page.table;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class StaticElement extends BuildInElement {

	public StaticElement() {
		super();
	}

	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException {
		try{
			
			Object value=getValueByName(data);
			themeItem.staticItem(pwriter,getDomId(id),value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

}
