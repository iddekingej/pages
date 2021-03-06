package org.elaya.page.widget.quickform;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;

public class TextAreaElement extends BuildInFormElement {
	String width="100%";
	String height="100px";
	
	public void setWidth(String pwidth){
		width=pwidth;
	}
	
	public void setHeight(String pheight){
		height=pheight;
	}
	
	public String getWidth()
	{
		return width;
	}
	
	public String getHeight()
	{
		return height;
	}
	
	@Override
	public String getJsClassName() {
		return "TTextareaElement";
	}

	@Override
	public void displayElement(int id,Writer pwriter,Data pdata) throws org.elaya.page.widget.Element.DisplayException  {
		try{			
			Object value=getValueByName(pdata);
			themeItem.textArea(pwriter,getDomId(id), getName(), getHeight(), getWidth(), value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

}
