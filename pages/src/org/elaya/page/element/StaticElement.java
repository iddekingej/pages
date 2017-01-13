package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class StaticElement extends PageElement<ElementThemeItem> {

	private String text;
	private boolean isHtml=false;
	private String className;
	private String css;
	
	public void setText(String ptext){
		text=ptext;
	}
	
	public void setIsHtml(Boolean pflag){
		isHtml=pflag;
	}
	
	public void setClassName(String pclassName){
		className=pclassName;
	}
	
	public void setCss(String pcss){
		css=pcss;
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
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException  {
		try{
			themeItem.staticElement(pwriter,pwriter.replaceVariables(data,text), isHtml, pwriter.replaceVariables(data,className), pwriter.replaceVariables(data,css));
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
