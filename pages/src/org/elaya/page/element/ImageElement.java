package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

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
	
	@Override
	public void display(Writer pwriter,Data pdata) throws Exception {
		Data data=getData(pdata);
		themeItem.image(pwriter,replaceVariables(data,url),replaceVariables(data,className),replaceVariables(data,css));
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
