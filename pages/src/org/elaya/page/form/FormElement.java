package org.elaya.page.form;

import org.elaya.page.*;

public abstract class FormElement<themeType> extends Element<themeType> {
		
	private String label;
	private String name;
	public String getLabel(){ return label;}
	public void setLabel(String p_label){ label=p_label;}
	public String getName(){ return name;}
	public void setName(String p_name){ name=p_name;}
	public boolean hasValue(){ return true;}
	public  void display() throws Exception
	{
		
	}
	
}
