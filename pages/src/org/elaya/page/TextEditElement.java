package org.elaya.page;

import java.util.Objects;

public class TextEditElement extends BuildInFormElement {

	@Override
	public void display(String p_value) throws Exception {
		Objects.requireNonNull(themeItem);		
		themeItem.textElement(getName(),p_value);		
	}

}
