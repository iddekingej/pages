package org.elaya.page.quickform;

import java.util.Objects;

public  class TextEditElement extends BuildInFormElement {

	int maxLength=-1;
	
	public TextEditElement()	
	{
		super();
	}
	
	public void setMaxLength(Integer p_size)
	{
		maxLength=p_size;
	}
	
	public int getMaxLength()
	{
		return maxLength;
	}
	
	@Override
	public void display(Object p_value) throws Exception {
		Objects.requireNonNull(themeItem);
		
		themeItem.textElement(getDomId(),getName(),p_value,maxLength);		
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
