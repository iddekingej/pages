package org.elaya.page.element;

import org.elaya.page.LinkType;
import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class LinkElement extends PageElement<ElementThemeItem> {
	private String url;
	private String text;
	private String className;
	private String css;
	private LinkType linkType=LinkType.LINK_APPLICATION;
	
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
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.Element.DisplayException {
		try{			
			String resultUrl=replaceVariables(data,url);

			if(linkType==LinkType.LINK_APPLICATION){
				resultUrl=pwriter.getBasePath()+resultUrl;
			}
			themeItem.link(pwriter,resultUrl, replaceVariables(data,text), replaceVariables(data,className), replaceVariables(data,css)); 
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
