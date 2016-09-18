package org.elaya.page.quickform;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

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
	public void display(Writer p_writer,Data p_data) throws Exception {
		Object l_value=getValueByName(p_data);
		themeItem.textArea(p_writer,getDomId(), getName(), getHeight(), getWidth(), l_value);
	}

}
