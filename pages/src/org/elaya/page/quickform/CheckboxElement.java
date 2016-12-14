package org.elaya.page.quickform;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class CheckboxElement extends BuildInFormElement {

	public CheckboxElement()
	{
		super();
	}

	@Override
	public void  display(Writer pwriter,Data pdata) throws Exception {
		Object value=getValueByName(pdata);
		themeItem.checkBoxElement(pwriter,getDomId(),getName(),value);

	}
	@Override
	public String getJsClassName() {
		return "TCheckboxElement";
	}

}
