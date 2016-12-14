package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class PanelElement extends PageElement<ElementThemeItem> {
	private String css;
	private String className;
	
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
	
	@Override
	public void display(Writer pwriter,Data pdata) throws Exception {
		Data data=getData(pdata);
		themeItem.panelHeader(pwriter,replaceVariables(data,className), replaceVariables(data,css));
		displaySubElements(pwriter,data);
		themeItem.panelFooter(pwriter);
	} 

	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
