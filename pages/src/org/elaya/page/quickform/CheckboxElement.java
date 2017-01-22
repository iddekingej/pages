package org.elaya.page.quickform;

import org.elaya.page.Writer;
import org.elaya.page.data.Data;

public class CheckboxElement extends BuildInFormElement {

	public CheckboxElement()
	{
		super();
	}

	@Override
	public void  displayElement(int id,Writer writer,Data data) throws org.elaya.page.Element.DisplayException{
		try{			
			Object value=getValueByName(data);
			themeItem.checkBoxElement(writer,getDomId(id),getName(),value);
		}catch(Exception e){
			throw new DisplayException("",e);
		}

	}
	@Override
	public String getJsClassName() {
		return "TCheckboxElement";
	}

}
