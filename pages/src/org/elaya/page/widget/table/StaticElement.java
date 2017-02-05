package org.elaya.page.widget.table;

import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;

public class StaticElement extends BuildInElement {

	public StaticElement() {
		super();
	}

	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{
			
			Object value=getValueByName(data);
			themeItem.staticItem(pwriter,getDomId(id),value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

}
