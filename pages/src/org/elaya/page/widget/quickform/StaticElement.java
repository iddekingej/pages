package org.elaya.page.widget.quickform;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;


public class StaticElement extends BuildInFormElement {
	private boolean isHtml=false;
	
	public StaticElement()
	{
		super();
	}
	public void setIsHtml(boolean pisHtml){
		isHtml=pisHtml;
	}
	
	public boolean getIsHtml(){
		return isHtml;
	}
	
	@Override
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException{
		try{			
			Object value=getValueByName(pdata);
			themeItem.staticElement(pwriter,getDomId(id),getName(),getIsHtml(),value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getJsClassName() {
		return "TStaticElement";
	}

}
