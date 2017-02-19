package org.elaya.page.widget.quickform;

import java.util.List;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;


public class SelectListElement extends OptionsElement {
	public SelectListElement()
	{
		super();
	}
	
	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{
			Object value=getValueByName(data);
			List<OptionItem> items=getOptions(data);

			themeItem.selectElement(pwriter,getDomId(id),getName(), items,value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getJsClassName() {
		return "TSelectListElement";
	}
}