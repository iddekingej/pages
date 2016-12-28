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
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException{
		try{
			themeItem.panelHeader(pwriter, className, css);
		}catch(Exception e){
			throw new DisplayException(e);
		}
	} 

	@Override
	public void displayElementFooter(int id,Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException{
		try{
			themeItem.panelFooter(pwriter);
		}catch(Exception e){
			throw new DisplayException(e);
		}
	} 
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
