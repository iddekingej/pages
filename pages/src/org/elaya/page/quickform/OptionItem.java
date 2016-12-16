package org.elaya.page.quickform;

public class OptionItem {
	private String value;
	private String text;
	
	public OptionItem(String pvalue,String ptext){
		value=pvalue;
		text=ptext;
	}
	
	public String getValue(){ return value;}
	public String getText(){ return text;}
	
	public void setValue(String pvalue){ value=pvalue;}
	public void setText(String ptext){ text=ptext;}
	

	

}
