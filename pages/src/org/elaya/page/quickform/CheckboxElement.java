package org.elaya.page.quickform;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class CheckboxElement extends BuildInFormElement {

	public CheckboxElement()
	{
		super();
	}

	@Override
	public void  display(Writer p_writer,Data p_data) throws Exception {
		Object l_value=getValueByName(p_data);
		themeItem.checkBoxElement(p_writer,getDomId(),getName(),l_value);

	}
	@Override
	public String getJsClassName() {
		return "TCheckboxElement";
	}

}
