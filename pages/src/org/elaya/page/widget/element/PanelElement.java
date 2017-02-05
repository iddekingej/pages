package org.elaya.page.widget.element;

import org.elaya.page.core.Writer;
import org.elaya.page.data.Data;
import org.elaya.page.widget.PageElement;


public class PanelElement extends PageElement<ElementThemeItem> {
	private String css;

	public void setCss(String pcss){
		css=pcss;
	}
	
	public String getCss()
	{
		return css;
	}
	
	@Override
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException{
		try{
			themeItem.panelHeader(pwriter,  css);
		}catch(Exception e){
			throw new DisplayException(e);
		}
	} 

	@Override
	public void displayElementFooter(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException{
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
