package org.elaya.page.widget.element;

import org.elaya.page.LinkType;
import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;
import org.elaya.page.widget.PageElement;

public class LinkElement extends PageElement<ElementThemeItem> {
	private String url;
	private String text;
	private String className;
	private String css;
	
	public void setUrl(String purl){
		url=purl;
	}
	
	
	public String getUrl()
	{
		return url;
	}
	
	public void setText(String ptext){
		text=ptext;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setClassName(String pclassName){
		className=pclassName;
	}
	
	public void setCss(String pcss){
		css=pcss;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public String getCss()
	{
		return css;
	}
	//TODO: id to element and js? 
	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{			
			String resultUrl=pwriter.processUrl(data,url);
			themeItem.link(pwriter,resultUrl, pwriter.replaceVariables(data,text), pwriter.replaceVariables(data,className), pwriter.replaceVariables(data,css)); 
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
