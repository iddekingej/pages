package org.elaya.page.quickform;

import java.util.Objects;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public  class TextEditElement extends BuildInFormElement {

	private int maxLength=-1;
	private boolean password=false;
	
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
	
	public void setPassword(Boolean p_password)
	{
		password=p_password;
	}
	
	public boolean getPassword()
	{
		return password;
	}

	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Objects.requireNonNull(themeItem);
		Object l_value=getValueByName(p_data);
		if(password){
			themeItem.passwordElement(p_writer,getDomId(),getName(),l_value,maxLength);
		} else {
			themeItem.textElement(p_writer,getDomId(),getName(),l_value,maxLength);
		}
	}
	@Override
	public String getJsClassName() {
		return "TTextEditElement";
	}
}
