package org.elaya.page.quickform;

import java.util.LinkedList;
import org.elaya.page.data.Data;

public class SelectListElement extends OptionsElement {

	

	public SelectListElement()
	{
		super();
	}
	
	@Override
	public void display(Data p_data) throws Exception {
		Data l_data=getData(p_data);
		Object l_value=getValueByName(l_data);
		LinkedList<OptionItem> l_items=getOptions(p_data);

		themeItem.selectElement(getDomId(),getName(), l_items,l_value);
	}

	@Override
	public String getJsType() {
		return "select";
	}

	@Override
	public String getJsClassName() {
		return "TSelectListElement";
	}
}