package org.elaya.page.element;

import org.elaya.page.Element;
import org.elaya.page.PageElement;

public class PanelElement extends PageElement<ElementThemeItem> {
	private String css;
	private String className;
	
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
		themeItem.panelHeader(className, css);
		for(Element<?> l_element:getElements()){
			l_element.display();
		}
		themeItem.panelFooter();
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
