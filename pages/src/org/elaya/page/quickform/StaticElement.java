package org.elaya.page.quickform;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;


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
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException{
		try{
			Object value=getValueByName(pdata);
			themeItem.staticElement(pwriter,getDomId(),getName(),getIsHtml(),value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getJsClassName() {
		return "TStaticElement";
	}

}
