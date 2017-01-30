package org.elaya.page.element;

import org.elaya.page.PageElement;
import org.elaya.page.core.Writer;

import java.io.IOException;

import org.elaya.page.Element;
import org.elaya.page.Errors;
import org.elaya.page.data.Data;
import org.elaya.page.data.Data.KeyNotFoundException;

public class RepeatElement extends PageElement<ElementThemeItem> {
	private String dataVariableName;
	
	public RepeatElement()
	{
		super();
		setIsNamespace(true);
		
	}
	
	@Override
	public void displayElement(int id,Writer writer, Data pdata) throws org.elaya.page.Element.DisplayException {
//Nothing to do for repeat element
	}
	
	@Override
	protected void preElement(int id,Writer writer,Data data,Element<?> element) throws IOException, KeyNotFoundException 
	{
		writer.getJSWriter().setFromOther("widgetParent", "widgetParent.newItem()");
	}
	
	@Override
	protected void postElement(int id,Writer writer,Data data,Element<?> element) throws IOException
	{		
		writer.getJSWriter().setFromOther("widgetParent","widgetParent.parent");
	}
	
	@Override
	public void displayChildElements(int id,Writer pwriter,Data pdata) throws DisplayException  
	{
		try{
			Data data=getData(pdata);
			Object dataValue=data.get(dataVariableName);
			if(dataValue instanceof Iterable){					
				Iterable<?> iter=(Iterable<?>)dataValue;
				for(Object item:iter){
					if(item instanceof Data){
						super.displayChildElements(id,pwriter, (Data)item);
					}else {
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
