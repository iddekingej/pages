package org.elaya.page;

public class CheckboxElement extends BuildInFormElement {



	@Override
	void display(String p_value) throws Exception {
		themeItem.checkBoxElement(getName(),p_value);

	}

}
