package org.elaya.page.quickform;

import java.util.List;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class SelectListElement extends OptionsElement {

	

	public SelectListElement()
	{
		super();
	}
	
	@Override
	public void display(Writer pwriter,Data pdata) throws Exception {
		Data data=getData(pdata);
		Object value=getValueByName(data);
		List<OptionItem> items=getOptions(pdata);

		themeItem.selectElement(pwriter,getDomId(),getName(), items,value);
	}

	@Override
	public String getJsClassName() {
		return "TSelectListElement";
	}
}