package org.elaya.page.widget.quickform;


import java.util.List;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;


public class RadioElement extends OptionsElement {
	private boolean isHorizontal=false;
	
	public RadioElement()
	{
		super();
	}
	
	
	public boolean getIsHorizontal(){ return isHorizontal;}
	public void setIsHorizontal(Boolean phorizontal){ isHorizontal=phorizontal;}

	@Override
	public void displayElement(int id,Writer pwriter,Data data) throws org.elaya.page.widget.Element.DisplayException {
		try{

			Object value=getValueByName(data);
			List<OptionItem> items=getOptions(data);
			themeItem.radioElement(pwriter,getDomId(id),getName(), items, isHorizontal,value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getJsClassName() {
		return "TRadioElement";
	}
	
}
