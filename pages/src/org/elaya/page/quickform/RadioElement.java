package org.elaya.page.quickform;


import java.util.List;
import org.elaya.page.Writer;
import org.elaya.page.data.Data;


public class RadioElement extends OptionsElement {
	private boolean isHorizontal=false;
	
	public RadioElement()
	{
		super();
	}
	
	
	public boolean getIsHorizontal(){ return isHorizontal;}
	public void setIsHorizontal(Boolean phorizontal){ isHorizontal=phorizontal;}

	@Override
	public void display(Writer pwriter,Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			Data data=getData(pdata);
			Object value=getValueByName(data);
			List<OptionItem> items=getOptions(data);
			themeItem.radioElement(pwriter,getDomId(),getName(), items, isHorizontal,value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getJsClassName() {
		return "TRadioElement";
	}
	
}
