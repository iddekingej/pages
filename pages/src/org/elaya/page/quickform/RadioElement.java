package org.elaya.page.quickform;

import java.util.LinkedList;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class RadioElement extends OptionsElement {
	private boolean isHorizontal=false;
	
	public RadioElement()
	{
		super();
	}
	
	
	public boolean getIsHorizontal(){ return isHorizontal;}
	public void setIsHorizontal(Boolean p_horizontal){ isHorizontal=p_horizontal;}

	@Override
	public void display(Writer p_writer,Data p_data) throws Exception {
		Data l_data=getData(p_data);
		Object l_value=getValueByName(l_data);
		LinkedList<OptionItem> l_items=getOptions(l_data);
		themeItem.radioElement(p_writer,getDomId(),getName(), l_items, isHorizontal,l_value);
	}

	@Override
	public String getJsClassName() {
		return "TRadioElement";
	}
	
}
