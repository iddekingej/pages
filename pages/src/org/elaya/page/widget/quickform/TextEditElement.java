package org.elaya.page.widget.quickform;

import java.util.Objects;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;

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
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException{
		try{		
			String domId=getDomId(id);
			Objects.requireNonNull(themeItem);
			Object value=getValueByName(pdata);
			if(password){
				themeItem.passwordElement(pwriter,domId,getName(),value,maxLength);
			} else {
				themeItem.textElement(pwriter,domId,getName(),value,maxLength);
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
