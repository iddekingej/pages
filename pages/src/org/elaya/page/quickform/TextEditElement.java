package org.elaya.page.quickform;

import java.util.Objects;

public  class TextEditElement extends BuildInFormElement {

	
	public TextEditElement()	
	{
		super();
	}
	
	@Override
	public void display(Object p_value) throws Exception {
		Objects.requireNonNull(themeItem);		
		themeItem.textElement(getDomId(),getName(),p_value);		
	}

	@Override
	public String getJsType() {
		return "text";
	}

	@Override
	public String getJsClassName() {
		return "TTextEditElement";
	}
}
