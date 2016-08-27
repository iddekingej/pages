package org.elaya.page.form;

public class StaticElement extends BuildInFormElement {
	private boolean isHtml;
	
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
	public void display(Object p_value) throws Exception {
		themeItem.StaticElement(getDomId(),getName(),getIsHtml(),p_value);
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
