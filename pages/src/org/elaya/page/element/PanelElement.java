package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.data.Data;

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
	public void display(Data p_data) throws Exception {
		Data l_data=getData(p_data);
		themeItem.panelHeader(replaceVariables(l_data,className), replaceVariables(l_data,css));
		displaySubElements(l_data);
		themeItem.panelFooter();
	}

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
