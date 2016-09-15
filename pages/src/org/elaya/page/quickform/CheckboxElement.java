package org.elaya.page.quickform;

import org.elaya.page.data.Data;

public class CheckboxElement extends BuildInFormElement {

	public CheckboxElement()
	{
		super();
	}


	@Override
	public void  display(Data p_data) throws Exception {
		Object l_value=getValueByName(p_data);
		themeItem.checkBoxElement(getDomId(),getName(),l_value);

	}


	@Override
	public String getJsType() {
		return "checkbox";
	}


	@Override
	public String getJsClassName() {
		return "TCheckboxElement";
	}

}
