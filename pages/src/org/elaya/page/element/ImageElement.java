package org.elaya.page.element;

import org.elaya.page.PageElement;

public class ImageElement extends PageElement<ElementThemeItem> {
	private String url;
	private String className;
	private String css;
	
	public void setUrl(String p_url){
		url=p_url;
	}
	
	public void setClassName(String p_className){
		className=p_className;
	}
	
	public void setCss(String p_css){
		css=p_css;
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
	public void display() throws Exception {
		themeItem.image(url,className,css);
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
