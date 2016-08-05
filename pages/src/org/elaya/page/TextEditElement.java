package org.elaya.page;

public class TextEditElement extends BuildInFormElement {

	@Override
	public void display(String p_value) throws Exception {
		themeItem.textElement(getName() ,p_value);
	}

}
