package org.elaya.page.widget.element;

import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.widget.PageElement;

public class ImageElement extends PageElement<ElementThemeItem> {
	private String url;
	private String className;
	private String css;
	
	public void setUrl(String purl){
		url=purl;
	}
	
	public void setClassName(String pclassName){
		className=pclassName;
	}
	
	public void setCss(String pcss){
		css=pcss;
	}
	
	public String getUrl()
	{
		return url;
	}
	
	public String getClassName()
	{
		return className;
	}
	
	public String getCss()
	{
		return css;
	}
	
	//TODO: add id to element andjs?
	@Override
	public void displayElement(int id,Writer writer,Data data) throws org.elaya.page.widget.Element.DisplayException{
		try{
			themeItem.image(writer,writer.processUrl(data,url),writer.replaceVariables(data,className),writer.replaceVariables(data,css));
		}catch(Exception e){
			throw new DisplayException(e);
		}
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
