package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.Writer;
import org.elaya.page.Errors;
import org.elaya.page.data.Data;

public class RepeatElement extends PageElement<ElementThemeItem> {
	private String dataVariableName;
	
	//TODO: Use new setup
	@Override
	public void displayElement(int id,Writer writer, Data pdata) throws org.elaya.page.Element.DisplayException {
		try{
			Data data=getData(pdata);
			Object dataValue=data.get(dataVariableName);
			if(dataValue instanceof Iterable){					
				Iterable<?> iter=(Iterable<?>)dataValue;
				for(Object item:iter){
					if(item instanceof Data){
						displaySubElements(writer,(Data)item);
					} else {
						throw new Errors.InvalidTypeException("element of dataVariable must be inherited from elaya.org.data.Data class");
					}
				}
			} else {
				throw new Errors.InvalidTypeException("dataVariable in repeat element must point to a iterator variable or null");
			}
		}catch(Exception e){
			throw new DisplayException("",e);
		}
	}

	@Override
	public String getJsClassName()
	{
		return "TRepeatElement";
	}
	
	public void setDataVariableName(String variableName)
	{
		dataVariableName=variableName;
	}
	
	public String getDataVariableName(){
		return dataVariableName;
	}
	
	@Override
	public String getThemeName() {
		return "ElementThemeItem";
	}

}
