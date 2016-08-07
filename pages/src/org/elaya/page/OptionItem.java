package org.elaya.page;

public class OptionItem {
	private String value;
	private String text;
	
	public String getValue(){ return value;}
	public String getText(){ return text;}
	
	public void setValue(String p_value){ value=p_value;}
	public void setText(String p_text){ text=p_text;}
	
	public OptionItem()
	{
		
	}
	
	
	public OptionItem(String p_value,String p_text){
		value=p_value;
		text=p_text;
	}
	

}
