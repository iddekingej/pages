package org.elaya.page.element;

import org.elaya.page.LinkType;
import org.elaya.page.PageElement;

public class LinkElement extends PageElement<ElementThemeItem> {
	private String url;
	private String text;
	private String className;
	private String css;
	private LinkType linkType=LinkType.LINK_APPLICATION;
	
	public void setUrl(String p_url){
		url=p_url;
	}
	
	
	public String getUrl()
	{
		return url;
	}
	
	public void setText(String p_text){
		text=p_text;
	}
	
	public String getText()
	{
		return text;
	}
	
	public void setClassName(String p_className){
		className=p_className;
	}
	
	public void setCss(String p_css){
		css=p_css;
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
		String l_url=url;
		if(linkType==LinkType.LINK_APPLICATION){
			l_url=themeItem.getApplication().getBasePath()+l_url;
		}
		themeItem.link(l_url, text, className, css); 
		
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
