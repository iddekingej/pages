package org.elaya.page;

public class OptionItem {
	private String value;
	private String text;
	
	public String getValue(){ return value;}
	public String getText(){ return text;}
	
	public OptionItem(String p_value,String p_text){
		value=p_value;
		text=p_text;
	}
	

}
