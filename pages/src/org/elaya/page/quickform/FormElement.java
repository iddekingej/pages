package org.elaya.page.quickform;

import org.elaya.page.*;

public abstract class FormElement<themeType extends ThemeItemBase> extends Element<themeType> {
	
	public FormElement()	
	{
		super();
	}
	

	public boolean hasValue(){ return true;}
	public  void display() throws Exception
	{
		//TODO error should be raised

	}

	
}
