package org.elaya.page.quickform;

import org.elaya.page.*;

public abstract class FormElement<themeType extends ThemeItemBase> extends Element<themeType> {
	
	public FormElement()	
	{
		super();
	}
	
	private String label;

	public String getLabel(){ return label;}
	public void setLabel(String p_label){ label=p_label;}
	public boolean hasValue(){ return true;}
	public  void display() throws Exception
	{
		//TODO error should be raised

	}
	abstract public String getJsType();
	
	
}
