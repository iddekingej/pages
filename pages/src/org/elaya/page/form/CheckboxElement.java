package org.elaya.page.form;

public class CheckboxElement extends BuildInFormElement {



	@Override
	public void  display(String p_value) throws Exception {
		themeItem.checkBoxElement(getName(),p_value);

	}

}
