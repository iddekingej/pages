package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.data.Data;

public class StaticElement extends PageElement<ElementThemeItem> {

	private String text;
	private boolean isHtml=false;
	private String className;
	private String css;
	
	public void setText(String p_text){
		text=p_text;
	}
	
	public void setIsHtml(Boolean p_flag){
		isHtml=p_flag;
	}
	
	public void setClassName(String p_className){
		className=p_className;
	}
	
	public void setCss(String p_css){
		css=p_css;
	}
	
	public String getText()
	{
		return text;
	}
	
	public boolean getIsHtml()
	{
		return isHtml;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public String getCss()
	{
		return css;
	}
	
	@Override
	public void display(Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.staticElement(replaceVariables(l_data,text), isHtml, replaceVariables(l_data,className), replaceVariables(l_data,css));
	}

	@Override
	public String getThemeName() {
		// TODO Auto-generated method stub
		return "ElementThemeItem";
	}

}
