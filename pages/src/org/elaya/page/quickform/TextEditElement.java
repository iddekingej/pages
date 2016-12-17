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
	
	public void setMaxLength(Integer psize)
	{
		maxLength=psize;
	}
	
	public int getMaxLength()
	{
		return maxLength;
	}
	
	public void setPassword(Boolean ppassword)
	{
		password=ppassword;
	}
	
	public boolean getPassword()
	{
		return password;
	}

	@Override
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException{
		try{
			Objects.requireNonNull(themeItem);
			Object value=getValueByName(pdata);
			if(password){
				themeItem.passwordElement(pwriter,getDomId(),getName(),value,maxLength);
			} else {
				themeItem.textElement(pwriter,getDomId(),getName(),value,maxLength);
			}
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	} 
	@Override
	public String getJsClassName() {
		return "TTextEditElement";
	}
}
