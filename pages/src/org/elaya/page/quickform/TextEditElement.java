package org.elaya.page.quickform;

import java.util.Objects;

import org.elaya.page.data.Data;

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
	public void display(Data p_data) throws Exception {
		Objects.requireNonNull(themeItem);
		Object l_value=getValueByName(p_data);
		themeItem.textElement(getDomId(),getName(),l_value,maxLength);		
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
