package org.elaya.page;

public class StaticElement extends BuildInFormElement {
	private boolean isHtml;
	
	public void setIsHtml(boolean p_isHtml){
		isHtml=p_isHtml;
	}
	
	public boolean getIsHtml(){
		return isHtml;
	}
	
	@Override
	public void display(String p_value) throws Exception {
		themeItem.StaticElement(getName(),getIsHtml(),p_value);
	}

}
