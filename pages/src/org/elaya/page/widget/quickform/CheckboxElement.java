package org.elaya.page.widget.quickform;

import org.elaya.page.core.Data;
import org.elaya.page.core.Writer;

public class CheckboxElement extends BuildInFormElement {

	public CheckboxElement()
	{
		super();
	}

	@Override
	public void  displayElement(int id,Writer writer,Data data) throws org.elaya.page.widget.Element.DisplayException{
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
