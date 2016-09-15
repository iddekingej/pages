package org.elaya.page.quickform;

import org.elaya.page.data.Data;

public class StaticElement extends BuildInFormElement {
	private boolean isHtml=false;
	
	public StaticElement()
	{
		super();
	}
	public void setIsHtml(boolean p_isHtml){
		isHtml=p_isHtml;
	}
	
	public boolean getIsHtml(){
		return isHtml;
	}
	
	@Override
	public void display(Data p_data) throws Exception {
		Object l_value=getValueByName(p_data);
		themeItem.StaticElement(getDomId(),getName(),getIsHtml(),l_value);
	}
	@Override
	public String getJsType() {

		return "static";
	}
	
	@Override
	public String getJsClassName() {
		return "TStaticElement";
	}

}
