package org.elaya.page.quickform;

public class TextAreaElement extends BuildInFormElement {
	String width="100%";
	String height="100px";
	
	public void setWidth(String p_width){
		width=p_width;
	}
	
	public void setHeight(String p_height){
		height=p_height;
	}
	
	public String getWidth()
	{
		return width;
	}
	
	public String getHeight()
	{
		return height;
	}
	
	public TextAreaElement() {
		super();
	}

	@Override
	public String getJsType() {
		return "textarea";
	}

	@Override
	public String getJsClassName() {
		// TODO Auto-generated method stub
		return "TTextareaElement";
	}

	@Override
	public void display(Object p_value) throws Exception {
		themeItem.textArea(getDomId(), getName(), getHeight(), getWidth(), p_value);
	}

}
